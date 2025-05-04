package com.example.chore_buddy.auth

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                Result.Success(it)
            } ?: Result.Error(Exception("Logowanie zakończone sukcesem, ale użytkownik jest null."))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Błąd logowania: ${e.message}")
            Result.Error(e)
        }
    }

    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let {
                Result.Success(it)
            } ?: Result.Error(Exception("Rejestracja zakończona sukcesem, ale użytkownik jest null."))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Błąd rejestracji: ${e.message}")
            Result.Error(e)
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Log.d("AuthRepository", "Link do resetowania hasła wysłany na email: $email")
            Result.Success(Unit) // Sukces!
        } catch (e: Exception) {
            Log.e("AuthRepository", "Błąd wysyłania linku resetującego hasło: ${e.message}")
            Result.Error(e) // Błąd
        }
    }

    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit> {
        val user = firebaseAuth.currentUser // Pobieramy aktualnie zalogowanego użytkownika

        return if (user != null) {
            if (user.providerData.none { it.providerId == EmailAuthProvider.PROVIDER_ID }) {
                val error = Exception("Zmiana hasła wymaga logowania email/hasło. Użytkownik zalogowany inną metodą.")
                Log.e("AuthRepository", error.message, error)
                return Result.Error(error)
            }

            val email = user.email
            if (email == null) {
                // To nie powinno się zdarzyć dla użytkownika email/hasło, ale warto sprawdzić
                val error = Exception("Adres email użytkownika jest niedostępny.")
                Log.e("AuthRepository", error.message, error)
                return Result.Error(error)
            }

            // Krok 1: Utworzenie danych uwierzytelniających ze starego hasła
            val credential = EmailAuthProvider.getCredential(email, oldPassword)

            try {
                // Krok 2: Ponowne uwierzytelnienie użytkownika przy użyciu starych danych
                user.reauthenticate(credential).await()
                Log.d("AuthRepository", "Użytkownik ponownie uwierzytelniony pomyślnie.")

                // Krok 3: Jeśli re-autentykacja się powiodła, zmień hasło na nowe
                user.updatePassword(newPassword).await()
                Log.d("AuthRepository", "Hasło zmienione pomyślnie.")

                Result.Success(Unit)

            } catch (e: FirebaseAuthInvalidCredentialsException) {
                // Specyficzny błąd: podano nieprawidłowe stare hasło
                val errorMessage = "Podane stare hasło jest nieprawidłowe."
                Log.e("AuthRepository", errorMessage, e)
                Result.Error(Exception(errorMessage, e)) // Zwracamy bardziej zrozumiały błąd
            } catch (e: Exception) {
                // Inne błędy podczas re-autentykacji lub zmiany hasła
                Log.e("AuthRepository", "Błąd podczas ponownego uwierzytelniania lub zmiany hasła: ${e.message}")
                Result.Error(e)
            }
        } else {
            // Nie ma zalogowanego użytkownika, nie można zmienić hasła
            val error = Exception("Brak zalogowanego użytkownika do zmiany hasła.")
            Log.e("AuthRepository", error.message, error)
            Result.Error(error)
        }
    }

    fun getCurrentUserInstant(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }
}
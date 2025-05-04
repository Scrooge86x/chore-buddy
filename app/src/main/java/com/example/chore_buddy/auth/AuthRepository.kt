package com.example.chore_buddy.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
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

    fun getCurrentUserInstant(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }
}
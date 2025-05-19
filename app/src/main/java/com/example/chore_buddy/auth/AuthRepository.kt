package com.example.chore_buddy.auth

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object AuthRepository {
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                AuthResult.Success(it)
            } ?: AuthResult.Error(Exception("Login successful, but the user is null."))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login error: ${e.message}")
            AuthResult.Error(e)
        }
    }

    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let {
                AuthResult.Success(it)
            } ?: AuthResult.Error(Exception("Registration successful, but the user is null."))
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.w("Registration", "User with the provided email already exists.", e)
            AuthResult.Error(e)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Registration error: ${e.message}")
            AuthResult.Error(e)
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    suspend fun sendPasswordResetEmail(email: String): AuthResult<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Log.d("AuthRepository", "Password reset link sent to email: $email")
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error sending password reset link: ${e.message}")
            AuthResult.Error(e)
        }
    }

    suspend fun changePassword(oldPassword: String, newPassword: String): AuthResult<Unit> {
        val user = firebaseAuth.currentUser

        return if (user != null) {
            if (user.providerData.none { it.providerId == EmailAuthProvider.PROVIDER_ID }) {
                val error = Exception("Changing password requires email/password login.")
                Log.e("AuthRepository", error.message, error)
                return AuthResult.Error(error)
            }

            val email = user.email
            if (email == null) {
                val error = Exception("User's email address is unavailable.")
                Log.e("AuthRepository", error.message, error)
                return AuthResult.Error(error)
            }

            val credential = EmailAuthProvider.getCredential(email, oldPassword)
            try {
                user.reauthenticate(credential).await()
                Log.d("AuthRepository", "User re-authenticated successfully.")

                user.updatePassword(newPassword).await()
                Log.d("AuthRepository", "Password changed successfully.")

                AuthResult.Success(Unit)

            } catch (e: FirebaseAuthInvalidCredentialsException) {
                val errorMessage = "The provided old password is incorrect."
                Log.e("AuthRepository", errorMessage, e)
                AuthResult.Error(Exception(errorMessage, e))
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error during re-authentication or password change: ${e.message}")
                AuthResult.Error(e)
            }
        } else {
            val error = Exception("No logged-in user to change password.")
            Log.e("AuthRepository", error.message, error)
            AuthResult.Error(error)
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    sealed class AuthResult<out T> {
        data class Success<out T>(val data: T) : AuthResult<T>()
        data class Error(val exception: Exception) : AuthResult<Nothing>()
    }
}
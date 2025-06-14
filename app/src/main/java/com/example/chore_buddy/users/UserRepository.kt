package com.example.chore_buddy.users

import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.firestore.FirestoreCollections
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object UserRepository {
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }

    suspend fun createUser(user: User): UserResult<Unit> {
        return try {
            firestore.collection(FirestoreCollections.USERS)
                .document(user.id)
                .set(user)
                .await()
            UserResult.Success(Unit)
        } catch (e: Exception) {
            UserResult.Error(e)
        }
    }

    suspend fun getCurrentUser(): UserResult<User?> {
        return try {
            val uid = AuthRepository.getCurrentUser()?.uid
            if (uid == null) {
                throw Exception("Current user uid was null.")
            }

            val document = firestore.collection(FirestoreCollections.USERS)
                .document(uid)
                .get()
                .await()
            UserResult.Success(document.toObject<User>())
        } catch (e: Exception) {
            UserResult.Error(e)
        }
    }

    suspend fun getUserByUid(uid: String): UserResult<User?> {
        return try {
            val document = firestore.collection(FirestoreCollections.USERS)
                .document(uid)
                .get()
                .await()
            UserResult.Success(document.toObject<User>())
        } catch (e: Exception) {
            UserResult.Error(e)
        }
    }

    suspend fun changeRole(role: String, userId: String): UserResult<Unit> {
        return try {
            val userRef = firestore.collection(FirestoreCollections.USERS).document(userId)
            firestore.runTransaction{ transaction ->
                transaction.update(userRef, "role", role)
            }.await()
            UserResult.Success(Unit)
        } catch (e: Exception) {
            UserResult.Error(e)
        }
    }

    sealed class UserResult<out T> {
        data class Success<out T>(val data: T) : UserResult<T>()
        data class Error(val exception: Exception) : UserResult<Nothing>()
    }
}

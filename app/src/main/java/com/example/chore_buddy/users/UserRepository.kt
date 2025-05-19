package com.example.chore_buddy.users

import com.example.chore_buddy.users.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object UserRepository {
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }
    private const val USERS_COLLECTION = "users"

    suspend fun createUser(user: User): UserResult<Unit> {
        return try {
            firestore.collection(USERS_COLLECTION)
                .document(user.id)
                .set(user)
                .await()
            UserResult.Success(Unit)
        } catch (e: Exception) {
            UserResult.Error(e)
        }
    }

    suspend fun getUserByUid(uid: String): UserResult<User?> {
        return try {
            val document = firestore.collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .await()
            UserResult.Success(document.toObject<User>())
        } catch (e: Exception) {
            UserResult.Error(e)
        }
    }

    suspend fun getUsersWithSameGroupId(currentUserGroupId: String): UserResult<List<User>> {
        return try {
            val querySnapshot = firestore.collection(USERS_COLLECTION)
                .whereEqualTo("groupId", currentUserGroupId)
                .get()
                .await()
            val users = querySnapshot.documents.mapNotNull { it.toObject<User>() }
            UserResult.Success(users)
        } catch (e: Exception) {
            UserResult.Error(e)
        }
    }

    sealed class UserResult<out T> {
        data class Success<out T>(val data: T) : UserResult<T>()
        data class Error(val exception: Exception) : UserResult<Nothing>()
    }
}
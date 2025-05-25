package com.example.chore_buddy.groups

import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.users.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

object GroupRepository {
    private val firestore: FirebaseFirestore by lazy {Firebase.firestore}

    /* TODO: change this Scrooge. */
    private const val USERS_COLLECTION = "users"
    private const val GROUP_COLLECTION = "groups"

    suspend fun createGroup(group: Group): GroupResult<Unit> {
        return try {
            val id = generateId()
            firestore.collection(GROUP_COLLECTION)
                .document(id)
                .set(group)
                .await()
            GroupResult.Success(Unit)
            joinGroup(AuthRepository.getCurrentUser()!!.uid, id)
        } catch (e: Exception) {
            GroupResult.Error(e);
        }
    }

    suspend fun getGroup(id: String): GroupResult<Group?> {
        return try {
            val document = firestore.collection(GROUP_COLLECTION)
                .document(id)
                .get()
                .await()
            GroupResult.Success(document.toObject<Group>())
        } catch (e: Exception) {
            GroupResult.Error(e)
        }
    }

    suspend fun getUsersWithSameGroupId(currentUserGroupId: String): GroupResult<List<User>> {
        return try {
            val querySnapshot = firestore.collection(USERS_COLLECTION)
                .whereEqualTo("groupId", currentUserGroupId)
                .get()
                .await()
            val users = querySnapshot.documents.mapNotNull { it.toObject<User>() }
            GroupResult.Success(users)
        } catch (e: Exception) {
            GroupResult.Error(e)
        }
    }

    suspend fun joinGroup(userId: String, groupId: String):  GroupResult<Unit>{
        return try {
            val userRef = firestore.collection(USERS_COLLECTION).document(userId)
            firestore.runTransaction { transaction ->
                transaction.update(userRef, "groupId", groupId)
            }.await()
            GroupResult.Success(Unit)
        } catch (e: Exception) {
            GroupResult.Error(e)
        }
    }

    private suspend fun generateId(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') - 'l' - 'O'
        val random = Random(System.currentTimeMillis())

        while (true) {
            val id = buildString {
                repeat(6) {
                    append(allowedChars[random.nextInt(allowedChars.size)])
                }
            }

            when (val result = getGroup(id)) {
                is GroupResult.Success -> {
                    if (result.data == null)
                        return id
                }
                is GroupResult.Error -> {
                    delay(10)
                }
            }
        }
    }

    sealed class GroupResult<out T> {
        data class Success<out T>(val data: T) : GroupResult<T>()
        data class Error(val exception: Exception) : GroupResult<Nothing>()
    }
}
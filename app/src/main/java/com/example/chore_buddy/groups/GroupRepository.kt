package com.example.chore_buddy.groups

import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.firestore.FirestoreCollections
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

    suspend fun createGroup(groupName: String): GroupResult<Unit> {
        return try {
            val groupId = generateId()
            firestore.collection(FirestoreCollections.GROUPS)
                .document()
                .set(Group(groupName, groupId))
                .await()
            GroupResult.Success(Unit)
            joinGroup(AuthRepository.getCurrentUser()!!.uid, groupId)
        } catch (e: Exception) {
            GroupResult.Error(e)
        }
    }

    suspend fun getGroup(groupId: String): GroupResult<Group?> {
        return try {
            val querySnapshot = firestore.collection(FirestoreCollections.GROUPS)
                .whereEqualTo("groupId", groupId)
                .limit(1)
                .get()
                .await()

            val document = querySnapshot.documents.firstOrNull()

            GroupResult.Success(document?.toObject<Group>())
        } catch (e: Exception) {
            GroupResult.Error(e)
        }
    }

    suspend fun getGroupMembers(groupId: String): GroupResult<List<User>> {
        return try {
            val querySnapshot = firestore.collection(FirestoreCollections.USERS)
                .whereEqualTo("groupId", groupId)
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
            val userRef = firestore.collection(FirestoreCollections.USERS).document(userId)
            firestore.runTransaction { transaction ->
                transaction.update(userRef, "groupId", groupId)
            }.await()
            GroupResult.Success(Unit)
        } catch (e: Exception) {
            GroupResult.Error(e)
        }
    }

    private suspend fun generateId(): String {
        val allowedChars = ('a'..'z') + ('0'..'9') - 'l'
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
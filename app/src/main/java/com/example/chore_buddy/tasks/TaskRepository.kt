package com.example.chore_buddy.tasks

import com.example.chore_buddy.firestore.FirestoreCollections

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.tasks.await
import java.util.Calendar

object TaskRepository {
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }

    suspend fun createTask(task: Task): TaskResult<Unit> {
        return try {
            firestore.collection(FirestoreCollections.TASKS)
                .document()
                .set(task)
                .await()

            TaskResult.Success(Unit)
        } catch (e: Exception) {
            TaskResult.Error(e)
        }
    }

    suspend fun getTasksForMonth(userId: String, year: Int, month: Int): TaskResult<List<Map<String, Any>>> {
        return try {
            val calendar = Calendar.getInstance().apply {
                set(year, month, 1, 0, 0, 0)
            }
            val startDate = Timestamp(calendar.time)

            calendar.set(year, month + 1, 0, 23, 59, 59)
            val endDate = Timestamp(calendar.time)

            val documents = firestore.collection(FirestoreCollections.TASKS)
                .whereEqualTo("assignedTo", userId)
                .whereGreaterThanOrEqualTo("dueDate", startDate)
                .whereLessThanOrEqualTo("dueDate", endDate)
                .get()
                .await()

            val result = documents.documents.map { it.data ?: emptyMap() }
            TaskResult.Success(result)
        } catch (e: Exception) {
            TaskResult.Error(e)
        }
    }

    sealed class TaskResult<out T> {
        data class Success<out T>(val data: T) : TaskResult<T>()
        data class Error(val exception: Exception) : TaskResult<Nothing>()
    }
}
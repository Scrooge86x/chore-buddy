package com.example.chore_buddy.tasks

import android.util.Log
import com.example.chore_buddy.firestore.FirestoreCollections

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.toObject

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

    suspend fun getTaskById(taskId: String): TaskResult<Task?> {
        return try {
            val document = firestore.collection(FirestoreCollections.TASKS)
                .document(taskId)
                .get()
                .await()

            TaskResult.Success(document.toObject<Task>())
        } catch (e: Exception) {
            TaskResult.Error(e)
        }
    }

    suspend fun getTasksForDay(groupId: String, year: Int, month: Int, day: Int): TaskResult<List<Task>> {
        return try {
            val calendar = Calendar.getInstance().apply {
                set(year, month, day, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val startDate = Timestamp(calendar.time)

            Log.d("startDate", startDate.toDate().toString())

            calendar.set(year, month, day, 23, 59, 59)
            val endDate = Timestamp(calendar.time)

            val documents = firestore.collection(FirestoreCollections.TASKS)
                .whereEqualTo("groupId", groupId)
                .whereGreaterThanOrEqualTo("dueDate", startDate)
                .whereLessThanOrEqualTo("dueDate", endDate)
                .get()
                .await()

            val result = documents.documents.mapNotNull { it.toObject<Task>() }

            TaskResult.Success(result)
        } catch (e: Exception) {
            TaskResult.Error(e)
        }
    }

    suspend fun getTasksForMonth(userId: String, year: Int, month: Int): TaskResult<List<Task>> {
        return try {
            val calendar = Calendar.getInstance().apply {
                set(year, month, 1, 0, 0, 0)
            }
            val startDate = Timestamp(calendar.time)

            calendar.set(year, month + 1, 0, 23, 59, 59)
            val endDate = Timestamp(calendar.time)

            val documents = firestore.collection(FirestoreCollections.TASKS)
                .whereEqualTo("assignedToId", userId)
                .whereGreaterThanOrEqualTo("dueDate", startDate)
                .whereLessThanOrEqualTo("dueDate", endDate)
                .get()
                .await()

            val result = documents.documents.mapNotNull { it.toObject<Task>() }
            TaskResult.Success(result)
        } catch (e: Exception) {
            TaskResult.Error(e)
        }
    }

    suspend fun updateTask(taskId: String, updates: Map<String, Any>): TaskResult<Unit> {
        return try {
            firestore.collection(FirestoreCollections.TASKS)
                .document(taskId)
                .set(updates, SetOptions.merge())
                .await()

            TaskResult.Success(Unit)
        } catch (e: Exception) {
            TaskResult.Error(e)
        }
    }

    sealed class TaskResult<out T> {
        data class Success<out T>(val data: T) : TaskResult<T>()
        data class Error(val exception: Exception) : TaskResult<Nothing>()
    }
}
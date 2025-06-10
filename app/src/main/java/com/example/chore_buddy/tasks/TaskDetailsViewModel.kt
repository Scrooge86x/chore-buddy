package com.example.chore_buddy.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chore_buddy.users.UserRepository
import kotlinx.coroutines.launch

class TaskDetailsViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var task by mutableStateOf<Task?>(null)
        private set

    var description by mutableStateOf<String?>(null)

    var isChecked by mutableStateOf<Boolean>(false)
        private set

    var isAdminOrSelf by mutableStateOf<Boolean>(false)
        private set

    fun getTask(taskId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                when (val result = TaskRepository.getTaskById(taskId)) {
                    is TaskRepository.TaskResult.Success -> {
                        task = result.data
                        description = task?.description ?: ""
                        isChecked = task?.status ?: false
                    }
                    is TaskRepository.TaskResult.Error -> errorMessage = result.exception.message ?:
                        "Unknown error occurred while getting task."
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun changeIsChecked(taskId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val update = mapOf("status" to !isChecked)

            try {
                when (val result = TaskRepository.updateTask(taskId, update)) {
                    is TaskRepository.TaskResult.Success -> isChecked = !isChecked
                    is TaskRepository.TaskResult.Error -> errorMessage = result.exception.message ?:
                        "Unknown error occurred while changing status"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateDescription(taskId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val update = mapOf("description" to description.toString())

            try {
                when (val result = TaskRepository.updateTask(taskId, update)) {
                    is TaskRepository.TaskResult.Success -> {
                        errorMessage = "Changing description ended successfully"
                        getTask(taskId)
                    }
                    is TaskRepository.TaskResult.Error -> errorMessage = result.exception.message ?:
                        "Unknown error occurred while changing description"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun checkIfIsAdminOrSelf() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                when (val result = UserRepository.getCurrentUser()) {
                    is UserRepository.UserResult.Success -> {
                        isAdminOrSelf = if (result.data?.role == "Admin" ||
                            result.data?.id == task?.assignedToId) true else false
                    }
                    is UserRepository.UserResult.Error -> errorMessage = result.exception.message ?:
                        "Unknown error occurred while checking if it's yours task or if you are Admin"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun resetError() {
        errorMessage = null
    }
}
package com.example.chore_buddy.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chore_buddy.users.User
import com.example.chore_buddy.users.UserRepository
import kotlinx.coroutines.launch

class DayInfoViewModel : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    var tasks by mutableStateOf<List<Task>>(emptyList())

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun getCurrentUser() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                when (val result = UserRepository.getCurrentUser()) {
                    is UserRepository.UserResult.Success -> user = result.data
                    is UserRepository.UserResult.Error -> errorMessage = result.exception.message ?:
                        "Failed to load user"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun getTasks(userId: String, year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                when (val result = TaskRepository.getTasksForDay(userId, year, month, day)) {
                    is TaskRepository.TaskResult.Success -> tasks = result.data
                    is TaskRepository.TaskResult.Error -> errorMessage = result.exception.message ?:
                        "Failed to get tasks"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            }
        }
    }

    fun resetError() {
        errorMessage = null
    }
}
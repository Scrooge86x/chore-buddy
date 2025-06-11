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

    suspend fun loadCurrentUser() {
        errorMessage = null
        try {
            when (val result = UserRepository.getCurrentUser()) {
                is UserRepository.UserResult.Success -> user = result.data
                is UserRepository.UserResult.Error -> errorMessage = result.exception.message ?:
                    "Failed to load user"
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "Unknown error occurred"
        }
    }

    fun getTasks(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            if (user == null) {
                loadCurrentUser()
            }

            try {
                val groupId = user?.groupId
                if (groupId == null) {
                    when (val result = TaskRepository.getCurrentUserTasksForDay(year, month, day)) {
                        is TaskRepository.TaskResult.Success -> tasks = result.data
                        is TaskRepository.TaskResult.Error -> errorMessage = result.exception.message ?:
                                "Failed to get tasks"
                    }
                } else {
                    when (val result = TaskRepository.getTasksForDay(groupId, year, month, day)) {
                        is TaskRepository.TaskResult.Success -> tasks = result.data
                        is TaskRepository.TaskResult.Error -> errorMessage = result.exception.message ?:
                                "Failed to get tasks"
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            }

            isLoading = false
        }
    }

    fun resetError() {
        errorMessage = null
    }
}
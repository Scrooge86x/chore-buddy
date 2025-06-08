package com.example.chore_buddy.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.users.User
import com.example.chore_buddy.users.UserRepository
import kotlinx.coroutines.launch

class CreateTaskViewModel : ViewModel() {
    var taskTitle by mutableStateOf("")
    var taskDescription by mutableStateOf("")
    var taskAssignedToId by mutableStateOf("")
    var taskAssignedToName by mutableStateOf("")
    var taskGroupId by mutableStateOf<String?>(null)

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var wasSuccessful by mutableStateOf(false)
        private set

    fun resetError() {
        errorMessage = null
    }

    fun loadTaskData() {
        viewModelScope.launch {
            try {
                var currentUser: User?
                when (val result = UserRepository.getCurrentUser()) {
                    is UserRepository.UserResult.Success -> {
                        currentUser = result.data
                    }
                    is UserRepository.UserResult.Error -> {
                        throw Exception(result.exception.message ?: "Unknown error occurred.")
                    }
                }
                if (currentUser == null) {
                    throw Exception("Current user was null.")
                }
                taskGroupId = currentUser.groupId
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred."
            }
        }
    }

    fun createCurrentTask() {
        viewModelScope.launch {
            errorMessage = null
            try {
                val currentUserUid = AuthRepository.getCurrentUser()?.uid
                if (currentUserUid.isNullOrEmpty()) {
                    throw Exception("Current user uid was invalid.")
                }

                val newTask = Task(
                    title = taskTitle,
                    description = taskDescription,
                    assignedToId = taskAssignedToId,
                    assignedToName = taskAssignedToName,
                    createdBy = currentUserUid,
                    groupId = taskGroupId,
                )

                when(val result = TaskRepository.createTask(newTask)) {
                    is TaskRepository.TaskResult.Success -> {
                        wasSuccessful = true
                    }
                    is TaskRepository.TaskResult.Error -> {
                        throw Exception(result.exception.message ?: "Unknown error occurred.")
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred."
            }
        }
    }
}
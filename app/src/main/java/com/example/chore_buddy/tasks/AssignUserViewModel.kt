package com.example.chore_buddy.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.chore_buddy.groups.GroupRepository
import com.example.chore_buddy.users.User
import com.example.chore_buddy.users.UserRepository
import com.example.chore_buddy.users.UserRepository.UserResult
import kotlinx.coroutines.launch

class AssignUserViewModel : ViewModel() {
    var users by mutableStateOf<List<User>?>(null)

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun resetError() {
        errorMessage = null
    }

    fun loadCurrentGroupMembers() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                var currentUser: User? = null
                when (val result = UserRepository.getCurrentUser()) {
                    is UserResult.Success -> currentUser = result.data
                    is UserResult.Error -> {
                        throw Exception(result.exception.message ?: "Failed to load user")
                    }
                }
                if (currentUser == null) {
                    throw Exception("For unknown reason there is no user.")
                }

                val groupId = currentUser.groupId ?: throw Exception("User is not in a group.")
                getMembers(groupId)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun getMembers(groupId: String) {
        when (val result = GroupRepository.getGroupMembers(groupId)) {
            is GroupRepository.GroupResult.Success -> {
                users = result.data
            }
            is GroupRepository.GroupResult.Error -> {
                throw Exception(result.exception.message ?: "Getting members error.")
            }
        }
    }
}
package com.example.chore_buddy.groups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.users.UserRepository
import kotlinx.coroutines.launch

class CreateOrJoinGroupViewModel : ViewModel() {
    enum class Success {
        No,
        Created,
        Joined
    }

    var groupName by mutableStateOf("")

    var groupId by mutableStateOf("")

    var isSuccess by mutableStateOf<Success>(Success.No)

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun createGroup() {
        if (groupName.isEmpty())
            return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                when (val result = GroupRepository.createGroup(groupName)) {
                    is GroupRepository.GroupResult.Success -> {
                        isSuccess = Success.Created
                        UserRepository.changeRole("Admin", AuthRepository.getCurrentUser()!!.uid)
                    }
                    is GroupRepository.GroupResult.Error -> {
                        errorMessage = result.exception.message ?: "Group creation error."
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun joinGroup() {
        if (groupId.isEmpty())
            return

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                when (val result = GroupRepository.joinGroup(AuthRepository.getCurrentUser()!!.uid, groupId)) {
                    is GroupRepository.GroupResult.Success -> {
                        isSuccess = Success.Joined
                    }
                    is GroupRepository.GroupResult.Error -> {
                        errorMessage = result.exception.message ?: "Group joining error."
                    }
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
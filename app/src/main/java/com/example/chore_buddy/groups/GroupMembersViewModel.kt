package com.example.chore_buddy.groups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.chore_buddy.users.User
import kotlinx.coroutines.launch

class GroupMembersViewModel : ViewModel() {

    var members by mutableStateOf<List<User>?>(null)
    var group by mutableStateOf<Group?>(null)

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun getGroup(groupId: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                when (val result = GroupRepository.getGroup(groupId)) {
                    is GroupRepository.GroupResult.Success -> {
                        group = result.data
                    }
                    is GroupRepository.GroupResult.Error -> {
                        errorMessage = result.exception.message ?: "Getting group error."
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun getMembers(groupId: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                when (val result = GroupRepository.getUsersWithSameGroupId(groupId)) {
                    is GroupRepository.GroupResult.Success -> {
                        members = result.data
                    }
                    is GroupRepository.GroupResult.Error -> {
                        errorMessage = result.exception.message ?: "Getting members error."
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
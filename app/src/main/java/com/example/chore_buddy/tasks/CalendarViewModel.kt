package com.example.chore_buddy.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.users.UserRepository
import kotlinx.coroutines.launch

class CalendarViewModel(): ViewModel() {

    var isInGroup by mutableStateOf<Boolean>(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        checkIfInGroup()
    }

    fun checkIfInGroup() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                when (val result =
                          UserRepository.getUserByUid(AuthRepository.getCurrentUser()!!.uid)) {
                    is UserRepository.UserResult.Success -> {
                        if (!result.data?.groupId.isNullOrBlank()) {
                            isInGroup = true
                        }
                    }
                    is UserRepository.UserResult.Error -> {
                        errorMessage = result.exception.message ?: "Unknown error occurred."
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred."
            }
        }
    }

    fun resetError() {
        errorMessage = null
    }
}
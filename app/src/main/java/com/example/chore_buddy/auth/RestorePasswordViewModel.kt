package com.example.chore_buddy.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chore_buddy.auth.AuthRepository.Result
import kotlinx.coroutines.launch

class RestorePasswordViewModel(): ViewModel() {
    var email by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var isSuccess by mutableStateOf<String?>(null)

    var errorMessage by mutableStateOf<String?>(null)

    fun sendRestoreEmail() {
        if (email.isEmpty()) {
            return
        }

        errorMessage = null
        isSuccess = null
        isLoading = true

        viewModelScope.launch {
            when (val result = AuthRepository.sendPasswordResetEmail(email)) {
                is Result.Success -> {
                    isSuccess = "Message was send to $email"
                }
                is Result.Error -> {
                    errorMessage = result.exception.message ?: "An error occurred while sending the email. Please try again."
                }
            }
        }
    }

    fun clearMessage() {
        errorMessage = null
        isSuccess = null
    }
}
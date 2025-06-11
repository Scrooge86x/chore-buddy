package com.example.chore_buddy.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.example.chore_buddy.auth.AuthRepository.AuthResult

class ChangePasswordViewModel(): ViewModel() {
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var newPasswordRepeat by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var passwordChangingSuccess by mutableStateOf<String?>(null)

    var errorMessage by mutableStateOf<String?>(null)

    fun changePassword() {
        if (!isPasswordValid()) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            when (val result = AuthRepository.changePassword(oldPassword, newPassword)) {
                is AuthResult.Success -> {
                    passwordChangingSuccess = "Password changed successfully."
                }
                is AuthResult.Error -> {
                    errorMessage = result.exception.message ?: "Changing password error."
                }
            }
            isLoading = false
        }
    }

    private fun isPasswordValid(): Boolean {
        return when {
            oldPassword.isEmpty() || newPassword.isEmpty() -> {
                errorMessage = "You need to give all passwords."
                false
            }
            newPassword != newPasswordRepeat -> {
                errorMessage = "Passwords don't match"
                false
            }
            newPassword.length < 6 -> {
                errorMessage = "Password must be at least 6 characters"
                false
            }
            newPassword == oldPassword -> {
                errorMessage = "New password MUST BE NEW"
                false
            }
            else -> true
        }
    }
}
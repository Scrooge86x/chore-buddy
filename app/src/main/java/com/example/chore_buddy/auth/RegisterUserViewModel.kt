package com.example.chore_buddy.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.example.chore_buddy.auth.AuthRepository.Result
import com.google.firebase.auth.FirebaseUser

class RegisterUserViewModel() : ViewModel() {
    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordRepeat by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var registrationSuccess by mutableStateOf<FirebaseUser?>(null)

    var errorMessage by mutableStateOf<String?>(null)

    fun registerUser() {
        if ( validate() ) {
            return
        }

        viewModelScope.launch {
            isLoading = true;
            errorMessage = null

            when (val result = AuthRepository.signUpWithEmailAndPassword(email, password)) {
                is Result.Success -> {
                    registrationSuccess = result.data
                }
                is Result.Error -> {
                    errorMessage = result.exception.message ?: "Registration error"
                }
            }

            isLoading = false
        }
    }

    private fun validate(): Boolean {
        return when {
            email.isEmpty() -> {
                errorMessage = "Email is required"
                true
            }
            password.isEmpty() -> {
                errorMessage = "Password is required"
                true
            }
            password.length < 6 -> {
                errorMessage = "Password must be at least 6 characters"
                true
            }
            !password.equals(passwordRepeat) -> {
                errorMessage = "Passwords don't match"
                true
            }
            else -> false
        }
    }

    fun resetError() {
        errorMessage = null
    }
}
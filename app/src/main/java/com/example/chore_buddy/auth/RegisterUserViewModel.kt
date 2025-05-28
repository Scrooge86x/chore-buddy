package com.example.chore_buddy.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.example.chore_buddy.auth.AuthRepository.AuthResult
import com.example.chore_buddy.users.User
import com.example.chore_buddy.users.UserRepository

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
        if (!isDataValid()) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            when (val result = AuthRepository.signUpWithEmailAndPassword(email, password)) {
                is AuthResult.Success -> {
                    val user = User(
                        result.data.uid,
                        username,
                        null,
                        email,
                        "User",
                        0,
                        3
                    )

                    when (val creationResult = UserRepository.createUser(user)) {
                        is UserRepository.UserResult.Success -> {
                            registrationSuccess = result.data
                        }
                        is UserRepository.UserResult.Error -> {
                            errorMessage = creationResult.exception.message ?: "User creation error."
                        }
                    }
                }
                is AuthResult.Error -> {
                    errorMessage = result.exception.message ?: "Registration error"
                }
            }

            isLoading = false
        }
    }

    private fun isDataValid(): Boolean {
        return when {
            username.isEmpty() -> {
                errorMessage = "Username is required"
                false
            }
            email.isEmpty() -> {
                errorMessage = "Email is required"
                false
            }
            password.isEmpty() -> {
                errorMessage = "Password is required"
                false
            }
            password.length < 6 -> {
                errorMessage = "Password must be at least 6 characters"
                false
            }
            password != passwordRepeat -> {
                errorMessage = "Passwords don't match"
                false
            }
            else -> true
        }
    }

    fun resetError() {
        errorMessage = null
    }
}
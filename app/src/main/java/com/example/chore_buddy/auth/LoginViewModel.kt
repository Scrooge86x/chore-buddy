package com.example.chore_buddy.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var loginError by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)

    fun signIn() {
        loginError = null
        loginSuccess = false

        viewModelScope.launch {
            if (email.isEmpty()) {
                loginError = "Email is required"
                return@launch
            }
            if (password.isEmpty()) {
                loginError = "Password is required"
                return@launch
            }

            isLoading = true
            when (val result = AuthRepository.signInWithEmailAndPassword(email, password)) {
                is AuthRepository.AuthResult.Success -> {
                    loginSuccess = true
                }
                is AuthRepository.AuthResult.Error -> {
                    loginError = when (result.exception) {
                        is com.google.firebase.auth.FirebaseAuthInvalidUserException -> "Użytkownik nie istnieje lub jest zablokowany."
                        is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -> "Nieprawidłowy email lub hasło."
                        else -> result.exception.message ?: "Wystąpił nieznany błąd logowania."
                    }
                }
            }
            isLoading = false
        }
    }
}

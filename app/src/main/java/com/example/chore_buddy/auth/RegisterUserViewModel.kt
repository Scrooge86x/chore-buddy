package com.example.chore_buddy.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

import com.example.chore_buddy.auth.AuthRepository.Result
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterUserViewModel() : ViewModel() {
    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordRepeat by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var registrationSuccess by mutableStateOf<FirebaseUser?>(null)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun registerUser() {
        if ( validate() ) {
            return
        }

        viewModelScope.launch {
            isLoading = true;
            _errorMessage.value = null

            when (val result = AuthRepository.signUpWithEmailAndPassword(email, password)) {
                is Result.Success -> {
                    registrationSuccess = result.data
                }
                is Result.Error -> {
                    _errorMessage.value = result.exception.message ?: "Registration error"
                }
            }

            isLoading = false
        }
    }

    private fun validate(): Boolean {
        return when {
            email.isEmpty() -> {
                _errorMessage.value = "Email is required"
                true
            }
            password.isEmpty() -> {
                _errorMessage.value = "Password is required"
                true
            }
            password.length < 6 -> {
                _errorMessage.value = "Password must be at least 6 characters"
                true
            }
            !password.equals(passwordRepeat) -> {
                _errorMessage.value = "Passwords don't match"
                true
            }
            else -> false
        }
    }

    fun resetError() {
        _errorMessage.value = null
    }
}
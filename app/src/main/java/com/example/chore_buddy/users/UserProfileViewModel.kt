package com.example.chore_buddy.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.users.UserRepository.UserResult
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {
    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    val isSelf: Boolean get() = user?.id == AuthRepository.getCurrentUser()?.uid

    fun loadUser(uid: String) {
        if (uid.isEmpty())
            return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                when (val result = UserRepository.getUserByUid(uid)) {
                    is UserResult.Success -> user = result.data
                    is UserResult.Error -> {
                        errorMessage = result.exception.message ?: "Failed to load user"
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun logout() {
        AuthRepository.signOut()
    }

    fun resetError() {
        errorMessage = null
    }
}

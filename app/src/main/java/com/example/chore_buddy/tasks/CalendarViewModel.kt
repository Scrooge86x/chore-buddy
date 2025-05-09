package com.example.chore_buddy.tasks

import androidx.lifecycle.ViewModel
import com.example.chore_buddy.auth.AuthRepository

class CalendarViewModel(): ViewModel() {
    fun logout() {
        AuthRepository.signOut()
    }
}
package com.example.chore_buddy.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateTaskViewModel : ViewModel() {
    var assignedUserId by mutableStateOf<String?>(null)
    var assignedUserName by mutableStateOf<String?>(null)
    var currentTask by mutableStateOf<Task>(Task())
}
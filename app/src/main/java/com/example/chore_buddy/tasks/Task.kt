package com.example.chore_buddy.tasks

import com.google.firebase.Timestamp

data class Task(
    var title: String = "",
    var description: String = "",
    var assignedTo: String = "",
    var createdBy: String = "",
    var groupId: String = "",
    var status: Boolean = true,
    var createdAt: Timestamp = Timestamp.now(),
    var dueDate: Timestamp = Timestamp.now()
)
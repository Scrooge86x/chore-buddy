package com.example.chore_buddy.tasks

import java.time.LocalDateTime

data class Task(
    var title: String = "",
    var description: String = "",
    var assignedTo: String = "",
    var createdBy: String = "",
    var groupId: String = "",
    var status: Boolean = true,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var dueDate: LocalDateTime = LocalDateTime.now()
)
package com.example.chore_buddy.tasks

import java.time.LocalDateTime

data class Task(
    val title: String = "",
    val description: String = "",
    val assignedTo: String = "",
    val createdBy: String = "",
    val groupId: String = "",
    val status: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val dueDate: LocalDateTime = LocalDateTime.now()
)
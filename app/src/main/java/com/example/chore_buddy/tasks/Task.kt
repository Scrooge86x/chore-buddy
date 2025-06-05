package com.example.chore_buddy.tasks

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId
    var title: String = "",
    var description: String = "",
    var assignedToId: String = "",
    var assignedToName: String = "",
    var createdBy: String = "",
    var groupId: String = "",
    var status: Boolean = true,
    var createdAt: Timestamp = Timestamp.now(),
    var dueDate: Timestamp = Timestamp.now()
)
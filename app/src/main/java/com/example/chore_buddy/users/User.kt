package com.example.chore_buddy.users

import com.google.firebase.firestore.DocumentId

data class User (
    @DocumentId
    val id: String = "",
    val name: String = "",
    val groupId: String? = null,
    val email: String = "",
    val role: String = "",
    val avatarIcon: Int = 0,
    val avatarResId: Int
)

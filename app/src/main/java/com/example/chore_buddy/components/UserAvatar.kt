package com.example.chore_buddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chore_buddy.R

@Composable
fun UserAvatar(imageResId: Int) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "User Avatar",
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color(0xFFAA88FF))
            .padding(4.dp)
            .clip(CircleShape)
    )
}

@Preview(
    name = "UserAvatar Preview - API 34",
    apiLevel = 34,
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun UserAvatarPreview() {
    UserAvatar(imageResId = R.drawable.avatar1)
}

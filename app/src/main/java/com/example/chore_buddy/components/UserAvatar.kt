package com.example.chore_buddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chore_buddy.R

fun avatarIndexToDrawable(index: Int): Int {
    return when(index) {
        0 -> R.drawable.avatar1
        1 -> R.drawable.avatar2
        2 -> R.drawable.avatar3
        3 -> R.drawable.avatar4
        4 -> R.drawable.avatar5
        5 -> R.drawable.avatar6
        6 -> R.drawable.avatar7
        7 -> R.drawable.avatar8
        8 -> R.drawable.avatar9
        9 -> R.drawable.avatar10
        10 -> R.drawable.avatar11
        else -> R.drawable.avatar1
    }
}

@Composable
fun UserAvatar(avatarIndex: Int) {
    val imageResId = avatarIndexToDrawable(avatarIndex)

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "User Avatar",
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color(0xFFAA88FF))
            .padding(4.dp)
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
    UserAvatar(avatarIndex = 0)
}

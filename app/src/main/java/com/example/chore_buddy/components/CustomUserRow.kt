package com.example.chore_buddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R

@Composable
fun CustomUserRow(userName: String, avatarResId: Int, onClick: () -> Unit = {}) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black)
            .clickable(onClick = onClick)
            .padding(horizontal = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        UserAvatar(imageResId = avatarResId)

        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = userName,
            fontSize = 22.sp,
            color = Color.LightGray,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomUserRowPreview() {
    Column(modifier = Modifier.background(Color.DarkGray)) {
        CustomUserRow(userName = "User 1", avatarResId = R.drawable.avatar1)
        CustomUserRow(userName = "User 2", avatarResId = R.drawable.avatar2)
        CustomUserRow(userName = "User 3", avatarResId = R.drawable.avatar3)
    }
}

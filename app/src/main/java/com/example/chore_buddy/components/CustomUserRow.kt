package com.example.chore_buddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState

@Composable
fun CustomUserRow(userName: String, avatarIcon: Int = -1, onClick: () -> Unit = {}) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(colorScheme.background)
            .clickable(onClick = onClick)
            .padding(horizontal = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        if (avatarIcon != -1) {
            UserAvatar(avatarIndex = avatarIcon)
        } else {
            Spacer(modifier = Modifier.width(56.dp))
        }
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = userName,
            fontSize = 22.sp,
            color = colorScheme.onBackground,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, name = "User Row Light", apiLevel = 34)
@Composable
fun CustomUserRowPreviewLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        Column(modifier = Modifier.background(colorScheme.background)) {
            CustomUserRow(userName = "User 1", avatarIcon = 0)
            CustomUserRow(userName = "User 2")
            CustomUserRow(userName = "User 3", avatarIcon = 2)
        }
    }
}

@Preview(showBackground = true, name = "User Row Dark", apiLevel = 34)
@Composable
fun CustomUserRowPreviewDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        Column(modifier = Modifier.background(colorScheme.background)) {
            CustomUserRow(userName = "User 1", avatarIcon = 0)
            CustomUserRow(userName = "User 2")
            CustomUserRow(userName = "User 3", avatarIcon = 2)
        }
    }
}

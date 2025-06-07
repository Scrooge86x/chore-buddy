package com.example.chore_buddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
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
fun NotYourTaskRow(taskName: String) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(colorScheme.background)
            .padding(start = 96.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskName,
            fontSize = 22.sp,
            color = colorScheme.onBackground,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium
        )
    }
}
@Preview(showBackground = true, name = "NotYourTask Light", apiLevel = 34)
@Composable
fun NotYourTaskRowPreviewLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        NotYourTaskRow(taskName = "Task for someone else")
    }
}
@Preview(showBackground = true, name = "NotYourTask Dark", apiLevel = 34)
@Composable
fun NotYourTaskRowPreviewDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        NotYourTaskRow(taskName = "Task for someone else")
    }
}

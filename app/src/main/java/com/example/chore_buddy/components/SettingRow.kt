package com.example.chore_buddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
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
fun SettingRow(
    taskName: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .border(
                    width = 2.dp,
                    color = colorScheme.outline,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = { onCheckedChange(!isChecked) }) {
                if (isChecked) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Checked",
                        tint = colorScheme.onSecondary,
                        modifier = Modifier
                            .background(colorScheme.secondary, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                            .fillMaxSize()
                            .padding(4.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = taskName,
            fontSize = 22.sp,
            color = colorScheme.onBackground,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium
        )
    }
}
@Preview(showBackground = true, apiLevel = 34)
@Composable
fun SettingRowPreviewLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        var isChecked by remember { mutableStateOf(true) }

        SettingRow(
            taskName = "Enable notifications",
            isChecked = isChecked,
            onCheckedChange = { isChecked = it }
        )
    }
}
@Preview(showBackground = true, apiLevel = 34)
@Composable
fun SettingRowPreviewDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        var isChecked by remember { mutableStateOf(true) }

        SettingRow(
            taskName = "Enable notifications",
            isChecked = isChecked,
            onCheckedChange = { isChecked = it }
        )
    }
}

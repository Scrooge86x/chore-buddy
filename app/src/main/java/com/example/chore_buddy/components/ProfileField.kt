package com.example.chore_buddy.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ProfileField(title: String, value: String, fontFamily: FontFamily) {
    Column {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = Color.Gray,
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal
        )
    }
}
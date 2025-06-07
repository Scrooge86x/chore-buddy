package com.example.chore_buddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R

@Composable
fun NotYourTaskRow(taskName: String) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black)
            //.padding(start = 24.dp),
            .padding(start = 96.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskName,
            fontSize = 22.sp,
            color = Color.LightGray,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium
        )
    }
}

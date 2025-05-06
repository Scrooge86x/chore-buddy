package com.example.chore_buddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
fun UserRow(userName: String) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black)
            .padding(horizontal = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(56.dp)
                .background(color = Color(0xFFAA88FF), shape = CircleShape)
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF66FFE5), shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = userName,
            fontSize = 22.sp, // większy tekst
            color = Color.LightGray,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium
        )
    }
}

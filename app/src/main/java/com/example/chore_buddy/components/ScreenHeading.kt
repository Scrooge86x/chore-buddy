package com.example.chore_buddy.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R

@Composable
fun ScreenHeading(
    text: String,
    modifier: Modifier = Modifier,
) {
    val interFontFamily = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.inter_regular),
    )

    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(top = 24.dp),
        style = TextStyle(
            fontFamily = interFontFamily
        )
    )
}

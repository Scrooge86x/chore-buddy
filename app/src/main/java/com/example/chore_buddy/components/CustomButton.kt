package com.example.chore_buddy.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    normalBackgroundColor: Color = Color(0xffa9a9a9),
    pressedBackgroundColor: Color = Color(0xff3c3b3b),
    textColor: Color = Color.White,
    height: Dp = 60.dp,
    cornerRadius: Dp = 16.dp
) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) pressedBackgroundColor else normalBackgroundColor,
            contentColor = if(isPressed) textColor else Color.Black
        )
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

    // Reset koloru po kliknięciu (jeśli chcesz żeby szybko wracał)
    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(150) // <- czas w ms jak długo kolor po kliknięciu
            isPressed = false
        }
    }
}

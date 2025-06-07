package com.example.chore_buddy.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R

@Composable
fun MultiLineInput(
    label: String = "",
    value: String,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Input text",
    placeholderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outline,
    textStyle: TextStyle = LocalTextStyle.current.copy(
        fontFamily = FontFamily.SansSerif,
        color = Color(0xffb79fff)
    )
) {
    val scrollState = rememberScrollState()
    val interFontFamily = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.inter_regular),
    )

    Text(
        text = label,
        //color = Color.White,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp),
        textAlign = TextAlign.Start,
        style = TextStyle(fontFamily = interFontFamily)
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholderText,
                color = placeholderColor,
                style = TextStyle(fontFamily = interFontFamily)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .verticalScroll(scrollState)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor
        ),
        textStyle = textStyle,
        singleLine = false,
        maxLines = Int.MAX_VALUE,
        enabled = isEnabled
    )
}

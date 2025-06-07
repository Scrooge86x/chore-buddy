package com.example.chore_buddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputCustomDialog(
    onDismiss: () -> Unit,
    onConfirm: (Date) -> Unit,
    initialHour: Int = 12,
    initialMinute: Int = 0,
    backgroundColor: Color = Color(0xFF1A1A1A),
    contentColor: Color = Color.White,
    confirmText: String = "OK",
    dismissText: String = "Cancel"
) {
    val state = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )


    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp),
            color = backgroundColor,
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Time",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                TimeInput(state = state)

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(dismissText, color = contentColor.copy(alpha = 0.7f))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        val cal = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, state.hour)
                            set(Calendar.MINUTE, state.minute)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        onConfirm(cal.time)
                    }) {
                        Text(confirmText, color = contentColor)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun TimeInputCustomDialogPreview() {
    TimeInputCustomDialog(
        onDismiss = {},
        onConfirm = {},
        backgroundColor = Color(0xFF121212),
        contentColor = Color(0xFFE0E0E0),
        confirmText = "OK",
        dismissText = "Cancel",
        initialHour = 14,
        initialMinute = 30
    )
}
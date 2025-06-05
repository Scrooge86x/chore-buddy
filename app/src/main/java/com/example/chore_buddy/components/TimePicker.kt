package com.example.chore_buddy.components

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TimePickerInput(
    label: String = "Due Time",
    time: LocalDateTime,
    onTimeChange: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    placeholderColor: Color = Color(0xffcccccc),
    focusedBorderColor: Color = Color(0xff76ffdf),
    unfocusedBorderColor: Color = Color(0xffcccccc),
    textStyle: TextStyle = LocalTextStyle.current.copy(
        fontFamily = FontFamily.SansSerif,
        color = Color(0xffb79fff)
    )
) {
    val context = LocalContext.current

    // Format czasu do "HH:mm"
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    var displayTime by remember { mutableStateOf(time.format(formatter)) }

    // Font
    val interFontFamily = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.inter_regular),
    )

    Text(
        text = label,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp),
        textAlign = TextAlign.Start,
        style = TextStyle(
            fontFamily = interFontFamily
        )
    )

    OutlinedTextField(
        value = displayTime,
        onValueChange = {}, // pole tylko do wyświetlania, więc puste
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                // pokaz TimePickerDialog
                val timePicker = TimePickerDialog(
                    context,
                    { _: TimePicker, hour: Int, minute: Int ->
                        val newDateTime = time.withHour(hour).withMinute(minute)
                        displayTime = newDateTime.format(formatter)
                        onTimeChange(newDateTime)
                    },
                    time.hour,
                    time.minute,
                    true
                )
                timePicker.show()
            },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            disabledTextColor = placeholderColor
        ),
        textStyle = textStyle,
        readOnly = true,
        singleLine = true,
        placeholder = {
            Text(
                text = "Select time",
                color = placeholderColor,
                style = TextStyle(
                    fontFamily = interFontFamily
                )
            )
        }
    )
}

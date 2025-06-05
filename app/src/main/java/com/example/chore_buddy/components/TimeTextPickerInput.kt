package com.example.chore_buddy.components

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import java.util.*

@Composable
fun TimePickerInput(
    label: String = "Select Time",
    time: String = "",
    onTimeChange: (String) -> Unit,
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

    val interFontFamily = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.inter_regular),
    )

    // Stan lokalny do wyświetlanej godziny
    var selectedTime by remember { mutableStateOf(time) }

    // Listener do TimePickerDialog
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _: TimePicker, hour: Int, minute: Int ->
                val formattedTime = String.format("%02d:%02d", hour, minute)
                selectedTime = formattedTime
                onTimeChange(formattedTime)
            },
            12, 0, true // domyślnie 12:00 i 24h format
        )
    }

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
        value = selectedTime,
        onValueChange = {}, // pole jest readOnly, więc zmiana ręczna zablokowana
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { timePickerDialog.show() },  // kliknięcie otwiera dialog
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
        ),
        textStyle = textStyle,
        readOnly = true, // blokujemy wpisywanie klawiaturą
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

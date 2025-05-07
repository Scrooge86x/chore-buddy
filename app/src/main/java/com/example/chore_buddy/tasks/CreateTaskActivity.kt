package com.example.chore_buddy.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.components.MultiLineInput
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

class CreateTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                CreateTaskScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun CreateTaskScreen() {
    var taskName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "New Task",
            color = Color.LightGray,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 12.dp),
            style = TextStyle(fontFamily = interFontFamily)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Task name",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(fontFamily = interFontFamily)
            )
            UserInput(
                value = taskName,
                onValueChange = { taskName = it }
            )
            Text(
                text = "Description",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                style = TextStyle(fontFamily = interFontFamily)
            )
            MultiLineInput(
                value = description,
                onValueChange = { description = it }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column{
            Spacer(modifier = Modifier.height(24.dp))
            CustomButton(text = "ASSIGN MEMBER", onClick = { /* assign logic */ })
            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(text = "CREATE", onClick = { /* create task logic */ })
            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(text = "CANCEL", onClick = { /* cancel logic */ })
        }
    }
}

package com.example.chore_buddy.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.components.MultiLineInput
import com.example.chore_buddy.components.ScreenHeading
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
    val activity = LocalActivity.current
    var taskName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(modifier = Modifier.height(32.dp))
        ScreenHeading(text = "New Task")
        Spacer(modifier = Modifier.height(32.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            UserInput(
                label = "Task name",
                value = taskName,
                onValueChange = { taskName = it }
            )
            MultiLineInput(
                label = "Description",
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
            CustomButton(text = "CANCEL", onClick = {
                activity?.finish()
            })
        }
    }
}

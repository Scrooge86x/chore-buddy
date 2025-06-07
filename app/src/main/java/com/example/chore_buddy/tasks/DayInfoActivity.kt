package com.example.chore_buddy.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserRow
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.components.NotYourTaskRow

class DayInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                DayInfoScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun DayInfoScreen() {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val tasks = listOf(
        false to "Not your task 1",
        false to "Not your task 2",
        true to "Your task 1",
        false to "Not your task 3",
        true to "Your task 2"
    )
    val date = "01/01/2000"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Logo()
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = date,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.White)
            tasks.forEach { (isUserTask, taskName) ->
                if (isUserTask) {
                    UserRow(userName = taskName)
                } else {
                    NotYourTaskRow(taskName = taskName)
                }
                HorizontalDivider(thickness = 1.dp, color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        FloatingActionButton(
            onClick = { /* TODO: action */ },
            containerColor = Color.LightGray,
            contentColor = Color.Black,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}

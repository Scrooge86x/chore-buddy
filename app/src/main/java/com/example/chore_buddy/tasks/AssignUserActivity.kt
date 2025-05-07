package com.example.chore_buddy.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

import com.example.chore_buddy.components.UserRow

class AssignUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                AssignUserScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun AssignUserScreen() {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val users = listOf("User 1", "User 2", "User 3", "User 4", "User 5")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Logo()
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Assign User",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(fontFamily = interFontFamily)
        )
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.White)
        users.forEach { user ->
            UserRow(userName = user)
            HorizontalDivider(thickness = 1.dp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}


package com.example.chore_buddy.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.components.PasswordInput
import com.example.chore_buddy.components.CustomButton

import com.example.chore_buddy.components.ScreenHeading


class RegisterUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                RegisterUserScreen()
            }
        }
    }
}


@Preview(apiLevel = 34)
@Composable
fun RegisterUserScreen() {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Logo()
        ScreenHeading(text = "Register")
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            UserInput(
                label = "Username",
                value = username,
                onValueChange = { username = it }
            )
            UserInput(
                label = "Email",
                value = email,
                onValueChange = { email = it }
            )
            PasswordInput(
                value = password,
                onValueChange = { password = it },
            )
            PasswordInput(
                label = "Repeat password",
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
            )
            Spacer(modifier = Modifier.height(32.dp))
            CustomButton(
                text = "REGISTER",
                onClick = { /* coś tam */ }
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

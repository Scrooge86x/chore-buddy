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
import android.util.Log
import android.widget.Toast

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

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.collectLatest
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
    val registerUserViewModel: RegisterUserViewModel = viewModel();

    val context = LocalContext.current
    LaunchedEffect(registerUserViewModel.errorMessage) {
        registerUserViewModel.errorMessage.collectLatest {message ->
            if (message != null) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                registerUserViewModel.resetError()
            }
        }
    }

    LaunchedEffect(registerUserViewModel.registrationSuccess) {
        Log.d("Registration", "Registration success")
    }

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
                value = registerUserViewModel.username,
                onValueChange = { registerUserViewModel.username = it }
            )
            UserInput(
                label = "Email",
                value = registerUserViewModel.email,
                onValueChange = { registerUserViewModel.email = it }
            )
            PasswordInput(
                value = registerUserViewModel.password,
                onValueChange = { registerUserViewModel.password = it },
            )
            PasswordInput(
                label = "Repeat password",
                value = registerUserViewModel.passwordRepeat,
                onValueChange = { registerUserViewModel.passwordRepeat = it },
            )
            Spacer(modifier = Modifier.height(32.dp))
            CustomButton(
                text = "REGISTER",
                onClick = { registerUserViewModel.registerUser() }
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

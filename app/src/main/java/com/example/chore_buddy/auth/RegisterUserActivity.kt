package com.example.chore_buddy.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.components.PasswordInput
import com.example.chore_buddy.components.CustomButton
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.chore_buddy.components.ScreenHeading
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme.colorScheme
import com.example.chore_buddy.ui.theme.ThemeState

class RegisterUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                RegisterUserScreen()
            }
        }
    }
}

@Composable
fun RegisterUserScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    val registerUserViewModel: RegisterUserViewModel = viewModel()

    LaunchedEffect(registerUserViewModel.errorMessage) {
        if (registerUserViewModel.errorMessage != null) {
            Toast.makeText(context, registerUserViewModel.errorMessage, Toast.LENGTH_LONG).show()
            registerUserViewModel.resetError()
        }
    }
    LaunchedEffect(registerUserViewModel.registrationSuccess) {
        if (registerUserViewModel.registrationSuccess != null) {
            Toast.makeText(context, "Account successfully created", Toast.LENGTH_LONG).show()
            activity?.finish()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
            .imePadding()
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

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 34
)
@Composable
fun RegisterUserScreenPreviewLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        RegisterUserScreen()
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 34
)
@Composable
fun RegisterUserScreenPreviewDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        RegisterUserScreen()
    }
}

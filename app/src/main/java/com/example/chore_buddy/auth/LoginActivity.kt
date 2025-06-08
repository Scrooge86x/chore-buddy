package com.example.chore_buddy.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.components.*
import com.example.chore_buddy.groups.CreateOrJoinGroupActivity
import com.example.chore_buddy.settings.MainSettingsActivity
import com.example.chore_buddy.tasks.CalendarActivity
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                LoginScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (AuthRepository.getCurrentUser() == null)
            return

        val intent = Intent(this, CalendarActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

@Composable
fun LoginScreen() {
    val loginViewModel: LoginViewModel = viewModel()
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    LaunchedEffect(loginViewModel.loginSuccess) {
        if (loginViewModel.loginSuccess) {
            val intent = Intent(context, CalendarActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
            activity?.finish()
        }
    }

    LaunchedEffect(loginViewModel.loginError) {
        if (loginViewModel.loginError != null) {
            Toast.makeText(context, loginViewModel.loginError, Toast.LENGTH_LONG).show()
            loginViewModel.loginError = null
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
        ScreenHeading(text = "Login")
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserInput(
                label = "Email",
                value = loginViewModel.email,
                onValueChange = { loginViewModel.email = it }
            )
            PasswordInput(
                value = loginViewModel.password,
                onValueChange = { loginViewModel.password = it },
            )
            Spacer(modifier = Modifier.height(160.dp))
            CustomButton(
                text = "LOGIN",
                onClick = { loginViewModel.signIn() }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    val intent = Intent(context, RestorePasswordActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text("RESET PASSWORD", color = colorScheme.onBackground)
            }
            TextButton(
                onClick = {
                    val intent = Intent(context, RegisterUserActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text("REGISTER ACCOUNT", color = colorScheme.onBackground)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    val intent = Intent(context, MainSettingsActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text("SETTINGS", color = colorScheme.onBackground)
            }
            TextButton(
                onClick = {
                    val intent = Intent(context, CreateOrJoinGroupActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text("WORK IN PROGRESS", color = colorScheme.onBackground)
            }
        }
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun PreviewLoginScreenDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        LoginScreen()
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun PreviewLoginScreenLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        LoginScreen()
    }
}

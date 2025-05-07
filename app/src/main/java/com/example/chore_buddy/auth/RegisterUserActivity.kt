package com.example.chore_buddy.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import com.example.chore_buddy.ui.theme.ChorebuddyTheme

import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.components.PasswordInput
import com.example.chore_buddy.components.CustomButton

import androidx.compose.ui.text.font.Font
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.R
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.collectLatest


class RegisterUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChorebuddyTheme {
                RegisterUserScreen()
            }
        }
    }
}


@Preview(apiLevel = 34)
@Composable
fun RegisterUserScreen() {
    val registerUserViewModel: RegisterUserViewModel = viewModel();

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
    )

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
        Spacer(modifier = Modifier.height(0.dp))

        Logo()

        Text(
            text = "Register",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp),

            style = TextStyle(
                fontFamily = interFontFamily
            )
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Username",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp),

                style = TextStyle(
                    fontFamily = interFontFamily
                )
            )

            UserInput(
                value = registerUserViewModel.username,
                onValueChange = { registerUserViewModel.username = it }
            )

            Text(
                text = "Email",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 16.dp),

                style = TextStyle(
                    fontFamily = interFontFamily
                )
            )

            UserInput(
                value = registerUserViewModel.email,
                onValueChange = { registerUserViewModel.email = it }
            )

            Text(
                text = "Password",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 16.dp),

                style = TextStyle(
                    fontFamily = interFontFamily
                )
            )

            PasswordInput(
                value = registerUserViewModel.password,
                onValueChange = { registerUserViewModel.password = it },
            )


            Text(
                text = "Repeat password",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 16.dp),

                style = TextStyle(
                    fontFamily = interFontFamily
                )
            )

            PasswordInput(
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

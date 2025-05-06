package com.example.chore_buddy.auth

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import com.example.chore_buddy.ui.theme.ChorebuddyTheme

import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.components.PasswordInput
import com.example.chore_buddy.components.CustomButton

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*

import com.example.chore_buddy.auth.LoginViewModel

import androidx.compose.ui.text.font.Font
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.R


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = AuthRepository.getCurrentUser()

        if (currentUser != null) {
            //val intent = Intent(this, CalendarActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            //startActivity(intent)
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            ChorebuddyTheme {
                LoginScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun LoginScreen() {
    val loginViewModel: LoginViewModel = viewModel()
    val loginSuccess = loginViewModel.loginSuccess

    val context = LocalContext.current

    val activity = context as? ComponentActivity

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            //val intent = Intent(context, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            //context.startActivity(intent)
            //activity?.finish()
            Log.d("LoginActivity", "Zalogowano")
        }
    }

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
    )

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
            text = "Login",
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
                text = "Email",
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
                value = loginViewModel.email,
                onValueChange = { loginViewModel.email = it }
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
                value = loginViewModel.password,
                onValueChange = { loginViewModel.password = it },
            )

            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))



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
                onClick = { /* TODO: Handle reset password */ }
            ) {
                Text("RESET PASSWORD", color = Color.White)
            }
            TextButton(
                onClick = { /* TODO: Handle register account */ }
            ) {
                Text("REGISTER ACCOUNT", color = Color.White)
            }
        }
    }
}

package com.example.chore_buddy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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



class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChorebuddyTheme {
                RegisterScreen()
            }
        }
    }
}


@Preview(apiLevel = 34)
@Composable
fun RegisterScreen() {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }


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

        //Logo
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

            // Username Input
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
                value = username,
                onValueChange = { username = it }
            )

            // Password Input
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
                value = email,
                onValueChange = { email = it }
            )

            // Password Input
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
                value = password,
                onValueChange = { password = it },
            )


            // Password Input
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
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Login Button
            CustomButton(
                text = "REGISTER",
                onClick = { /* coś tam */ }
            )


            Spacer(modifier = Modifier.height(16.dp))
        }
        //Spacer(modifier = Modifier.height(32.dp))
    }
}

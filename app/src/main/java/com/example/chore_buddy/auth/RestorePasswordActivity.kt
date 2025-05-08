package com.example.chore_buddy.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.chore_buddy.components.CustomButton

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.components.ScreenHeading


class RestorePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                RestorePasswordScreen()
            }
        }
    }
}


@Preview(apiLevel = 34)
@Composable
fun RestorePasswordScreen() {
    val restorePasswordViewModel: RestorePasswordViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Logo()
        ScreenHeading(text = "Restore Password")
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(96.dp))
            UserInput(
                label = "Email",
                value = restorePasswordViewModel.email,
                onValueChange = { restorePasswordViewModel.email = it }
            )
            Spacer(modifier = Modifier.height(224.dp))
            CustomButton(
                text = "SEND RESET EMAIL",
                onClick = { restorePasswordViewModel.sendRestoreEmail() }
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

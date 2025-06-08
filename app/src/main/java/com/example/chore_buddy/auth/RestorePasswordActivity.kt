package com.example.chore_buddy.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.components.CustomButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.ui.theme.ThemeState

class RestorePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                RestorePasswordScreen()
            }
        }
    }
}


@Composable
fun RestorePasswordScreen() {
    val restorePasswordViewModel: RestorePasswordViewModel = viewModel()

    val context = LocalContext.current
    LaunchedEffect(restorePasswordViewModel.errorMessage) {
        if (restorePasswordViewModel.errorMessage != null) {
            Toast.makeText(context, restorePasswordViewModel.errorMessage, Toast.LENGTH_LONG).show()
            restorePasswordViewModel.clearMessage()
        }
    }

    LaunchedEffect(restorePasswordViewModel.isSuccess) {
        if (restorePasswordViewModel.isSuccess != null) {
            Toast.makeText(context, restorePasswordViewModel.isSuccess, Toast.LENGTH_LONG).show()
            restorePasswordViewModel.clearMessage()
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

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 34
)
@Composable
fun RestorePasswordScreenPreviewLight() {
    ChoreBuddyTheme(darkTheme = false) {
        RestorePasswordScreen()
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 34
)
@Composable
fun RestorePasswordScreenPreviewDark() {
    ChoreBuddyTheme(darkTheme = true) {
        RestorePasswordScreen()
    }
}


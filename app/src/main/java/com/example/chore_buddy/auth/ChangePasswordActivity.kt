package com.example.chore_buddy.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.PasswordInput
import com.example.chore_buddy.components.CustomButton

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.components.ScreenHeading


class ChangePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                ChangePasswordScreen()
            }
        }
    }
}


@Preview(apiLevel = 34)
@Composable
fun ChangePasswordScreen() {
    var changePasswordViewModel: ChangePasswordViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(changePasswordViewModel.passwordChangingSuccess) {
        if (changePasswordViewModel.passwordChangingSuccess != null) {
            Toast.makeText(
                context,
                changePasswordViewModel.passwordChangingSuccess,
                Toast.LENGTH_LONG
            ).show()
            changePasswordViewModel.newPassword = ""
            changePasswordViewModel.newPasswordRepeat = ""
            changePasswordViewModel.oldPassword = ""
            changePasswordViewModel.errorMessage = null
            changePasswordViewModel.passwordChangingSuccess = null
        }
    }

    LaunchedEffect(changePasswordViewModel.errorMessage) {
        if (changePasswordViewModel.errorMessage != null) {
        Toast.makeText(context, changePasswordViewModel.errorMessage, Toast.LENGTH_LONG).show()
        changePasswordViewModel.errorMessage = null
            }
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
        Spacer(modifier = Modifier.height(16.dp))
        ScreenHeading(text = "Change Password")
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            PasswordInput(
                label = "Old Password",
                value = changePasswordViewModel.oldPassword,
                onValueChange = { changePasswordViewModel.oldPassword = it },
            )
            PasswordInput(
                label = "New Password",
                value = changePasswordViewModel.newPassword,
                onValueChange = { changePasswordViewModel.newPassword = it },
            )
            PasswordInput(
                label = "Repeat New Password",
                value = changePasswordViewModel.newPasswordRepeat,
                onValueChange = { changePasswordViewModel.newPasswordRepeat = it },
            )
            Spacer(modifier = Modifier.height(96.dp))
            CustomButton(
                text = "CHANGE PASSWORD",
                onClick = { changePasswordViewModel.changePassword() }
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

package com.example.chore_buddy.tasks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
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
import com.example.chore_buddy.components.CustomUserRow
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState

import com.example.chore_buddy.users.User

class AssignUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                AssignUserScreen()
            }
        }
    }
}

@Composable
fun AssignUserScreen() {
    val context = LocalContext.current
    val assignUserViewModel: AssignUserViewModel = viewModel()

    LaunchedEffect(assignUserViewModel.errorMessage) {
        if (assignUserViewModel.errorMessage != null) {
            Toast.makeText(
                context,
                assignUserViewModel.errorMessage,
                Toast.LENGTH_LONG
            ).show()
            assignUserViewModel.resetError()
        }
    }
    assignUserViewModel.loadCurrentGroupMembers()

    val users = assignUserViewModel.users
    if (users != null) {
        AssignUserContent(
            users = users
        )
    }
}

@Composable
fun AssignUserContent(users: List<User>) {
    val activity = LocalActivity.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .background(colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        ScreenHeading(text = "Assign User")
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 1.dp, color = colorScheme.onBackground)
        users.forEach { user ->
            CustomUserRow(
                userName = user.name,
                avatarIndex = 1,
                onClick = {
                    val returnedValue = Intent().apply {
                        putExtra("USER_ID", user.id)
                        putExtra("USER_NAME", user.name)
                    }
                    activity?.setResult(Activity.RESULT_OK, returnedValue)
                    activity?.finish()
                }
            )
            HorizontalDivider(thickness = 1.dp, color = colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

fun sampleUsers() = listOf(
    User(id = "1", name = "User 1", groupId = "group2", email = "admin@example.com", role = "Admin"),
    User(id = "2", name = "User 2", groupId = "group2", email = "jan@example.com", role = "User"),
    User(id = "3", name = "User 3", groupId = "group2", email = "anna@example.com", role = "User"),
    User(id = "4", name = "User 4", groupId = "group2", email = "piotr@example.com", role = "User"),
    User(id = "5", name = "User 5", groupId = "group2", email = "maria@example.com", role = "User")
)

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun PreviewMainSettingsScreenLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        AssignUserContent(
            sampleUsers()
        )
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun PreviewMainSettingsScreenDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        AssignUserContent(
            sampleUsers()
        )
    }
}

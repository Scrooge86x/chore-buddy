package com.example.chore_buddy.tasks

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.auth.ChangePasswordActivity
import com.example.chore_buddy.auth.LoginActivity
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                CalendarScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun CalendarScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val calendarViewModel: CalendarViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ScreenHeading(text = "under construction")
        CustomButton(
            text = "CHANGE PASSWORD",
            onClick = {
                val intent = Intent(context, ChangePasswordActivity::class.java)
                context.startActivity(intent)
            }
        )
        CustomButton(
            text = "Logout",
            onClick = {
                calendarViewModel.logout()
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
            }
        )
        ScreenHeading(text = "under construction")
    }
}

package com.example.chore_buddy.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.NotYourTaskRow
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

class MainSettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChoreBuddyTheme {
                MainSettingsScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun MainSettingsScreen() {
    val userSettings = listOf("Setting 1", "Setting 2", "Setting 3", "Setting 4")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            ScreenHeading(text = "Settings")
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.White)
            userSettings.forEach { userSet ->
                NotYourTaskRow(taskName = userSet)
                HorizontalDivider(thickness = 1.dp, color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column (
                modifier = Modifier.padding(24.dp)
            ) {
                CustomButton(
                    text = "SAVE",
                    onClick = { /* TODO: Change password */ }
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(
                    text = "CANCEL",
                    onClick = { /* TODO: Logout */ }
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(
                    text = "RESTORE DEFAULT",
                    onClick = { /* TODO: Logout */ }
                )
            }
        }
    }
}

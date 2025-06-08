package com.example.chore_buddy.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.components.SettingRow
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState

class MainSettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                MainSettingsScreen()
            }
        }
    }
}

@Composable
fun MainSettingsScreen() {
    var resetTrigger by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            ScreenHeading(text = "Settings")
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = colorScheme.onBackground)
            SettingRow(
                taskName = "Dark Theme",
                initialState = ThemeState.isDarkTheme,
                resetTrigger = resetTrigger,
                onCheckedChange = { isChecked ->
                    ThemeState.isDarkTheme = isChecked
                }
            )
            HorizontalDivider(thickness = 1.dp, color = colorScheme.onBackground)
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(
                    text = "RESTORE DEFAULT",
                    onClick = {
                        resetTrigger = !resetTrigger
                        ThemeState.isDarkTheme = true
                    }
                )
            }
        }
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun PreviewMainSettingsScreenLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        MainSettingsScreen()
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun PreviewMainSettingsScreenDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        MainSettingsScreen()
    }
}
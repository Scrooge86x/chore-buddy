package com.example.chore_buddy.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
            val darkTheme by remember { derivedStateOf { ThemeState.isDarkTheme } }
            ChoreBuddyTheme(darkTheme = darkTheme) {
                MainSettingsScreen(
                    isDarkTheme = ThemeState.isDarkTheme,
                    onThemeChange = { ThemeState.isDarkTheme = it }
                )
            }
        }
    }
}
@Composable
fun MainSettingsScreen(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val userSettings = listOf("Dark Theme", "Setting 2", "Setting 3", "Setting 4")
    val checkedStates = remember {
        mutableStateListOf(
            isDarkTheme,
            false,
            false,
            false
        )
    }
    val colorScheme = MaterialTheme.colorScheme
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

            userSettings.forEachIndexed { index, settingName ->
                SettingRow(
                    taskName = settingName,
                    isChecked = checkedStates[index],
                    onCheckedChange = { isChecked ->
                        checkedStates[index] = isChecked
                        if (index == 0) {
                            onThemeChange(isChecked)
                        }
                    }
                )
                HorizontalDivider(thickness = 1.dp, color = colorScheme.onBackground)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                CustomButton(
                    text = "SAVE",
                    onClick = {
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(
                    text = "CANCEL",
                    onClick = {
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(
                    text = "RESTORE DEFAULT",
                    onClick = {
                        checkedStates.replaceAll { false }
                        onThemeChange(false)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun PreviewMainSettingsScreenLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        MainSettingsScreen(
            isDarkTheme = ThemeState.isDarkTheme,
            onThemeChange = {}
        )
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun PreviewMainSettingsScreenDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        MainSettingsScreen(
            isDarkTheme = ThemeState.isDarkTheme,
            onThemeChange = {}
        )
    }
}
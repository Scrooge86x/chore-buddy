package com.example.chore_buddy.users

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.ProfileField
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

class UserProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChoreBuddyTheme {
                UserProfileScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun UserProfileScreen() {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val date = "01/01/2000"
    val email = "user@lorem.ipsum"
    val role = "Admin"
    val self = false
    val admin = true

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Logo(modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(32.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(color = Color(0xFFAA88FF), shape = CircleShape)
                        .padding(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFF66FFE5), shape = CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Username",
                    color = Color.LightGray,
                    fontSize = 26.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            ProfileField("Creation date", date, interFontFamily)
            Spacer(modifier = Modifier.height(64.dp))
            ProfileField("User email", email, interFontFamily)
            Spacer(modifier = Modifier.height(64.dp))
            ProfileField("User role", role, interFontFamily)
            Spacer(modifier = Modifier.weight(1f))
            if (self) {
                Column {
                    CustomButton(
                        text = "CHANGE PASSWORD",
                        onClick = { /* TODO: Change password */ }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomButton(
                        text = "LOGOUT",
                        onClick = { /* TODO: Logout */ }
                    )
                }
            }
            if (admin) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomButton(
                        text = "REMOVE FROM GROUP",
                        onClick = { /* TODO: Logout */ }
                    )
                }
            }
        }
    }
}

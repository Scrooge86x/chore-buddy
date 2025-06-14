package com.example.chore_buddy.users

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.R
import com.example.chore_buddy.auth.ChangePasswordActivity
import com.example.chore_buddy.auth.LoginActivity
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.ProfileField
import com.example.chore_buddy.components.UserAvatar
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState

class UserProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                Surface {
                    UserProfileScreen(intent.getStringExtra("USER_ID"))
                }
            }
        }
    }
}

@Composable
fun UserProfileScreen(userId: String?) {
    userId ?: return

    val context = LocalContext.current
    var userProfileViewModel: UserProfileViewModel = viewModel()
    userProfileViewModel.loadUser(userId)

    LaunchedEffect(userProfileViewModel.errorMessage) {
        userProfileViewModel.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            userProfileViewModel.resetError()
        }
    }

    val user = userProfileViewModel.user
    UserProfileContent(
        user = user ?: User(
            name = "Invalid User",
            email = "Not registered yet",
            role = "Unassigned",
        ),
        isSelf = userProfileViewModel.isSelf,
        userProfileViewModel = userProfileViewModel
    )
}

@Composable
fun UserProfileContent(
    user: User,
    isSelf: Boolean,
    userProfileViewModel: UserProfileViewModel? = null
) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val context = LocalContext.current
    val activity = LocalActivity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Logo(modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(32.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                UserAvatar(user.avatarIcon)
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = user.name,
                    color = colorScheme.onBackground,
                    fontSize = 26.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            ProfileField("User email", user.email, interFontFamily)
            if (user.groupId != null) {
                Spacer(modifier = Modifier.height(64.dp))
                ProfileField("User role", user.role, interFontFamily)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (isSelf) {
                Column {
                    CustomButton(
                        text = "CHANGE PASSWORD",
                        onClick = {
                            context.startActivity(Intent(context, ChangePasswordActivity::class.java))
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (user.groupId != null) {
                        CustomButton(
                            text = "LEAVE GROUP",
                            onClick = { userProfileViewModel?.leaveGroup(user.id) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    CustomButton(
                        text = "LOGOUT",
                        onClick = {
                            userProfileViewModel?.logout()
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)
                            activity?.finish()
                        }
                    )
                }
            }
            if (userProfileViewModel?.isAdminView == true && !isSelf) {
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(
                    text = "REMOVE FROM GROUP",
                    onClick = { userProfileViewModel.leaveGroup(user.id) }
                )
            }
        }
    }
}

@Preview(name = "User Profile - Light", showBackground = true, apiLevel = 34)
@Composable
fun UserProfileLightPreview() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        Surface {
            UserProfileContent(
                user = User(
                    id = "12345",
                    name = "TestUser",
                    groupId = "a1b2c3",
                    email = "user@example.org",
                    role = "User"
                ),
                isSelf = true
            )
        }
    }
}

@Preview(name = "User Profile - Dark", showBackground = true, apiLevel = 34)
@Composable
fun UserProfileDarkPreview() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        Surface {
            UserProfileContent(
                user = User(
                    id = "12345",
                    name = "TestUser",
                    groupId = "a1b2c3",
                    email = "user@example.org",
                    role = "User"
                ),
                isSelf = true
            )
        }
    }
}

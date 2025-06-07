package com.example.chore_buddy.users

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

class UserProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChoreBuddyTheme {
                UserProfileScreen(intent.getStringExtra("USER_ID"))
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
        if (userProfileViewModel.errorMessage != null) {
            Toast.makeText(
                context,
                userProfileViewModel.errorMessage,
                Toast.LENGTH_LONG
            ).show()
            userProfileViewModel.resetError()
        }
    }

    val user = userProfileViewModel.user
    if (user != null) {
        UserProfileContent(
            user = user,
            isSelf = userProfileViewModel.isSelf,
            userProfileViewModel = userProfileViewModel
        )
    } else {
        UserProfileContent(User(
            name = "Invalid User",
            email = "Not registered yet",
            role = "Unassigned",
        ), false)
    }
}

@Preview(apiLevel = 34)
@Composable
fun UserProfilePreview() {
    ChoreBuddyTheme {
        UserProfileContent(User(
            id = "12345",
            name = "TestUser",
            groupId = "a1b2c3",
            email = "user@example.org",
            role = "User",
        ), true)
    }
}

@Composable
fun UserProfileContent(
    user: User,
    isSelf: Boolean,
    userProfileViewModel: UserProfileViewModel? = null
) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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
//                Box(
//                    modifier = Modifier
//                        .size(64.dp)
//                        .background(color = Color(0xFFAA88FF), shape = CircleShape)
//                        .padding(6.dp)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(color = Color(0xFF66FFE5), shape = CircleShape)
//                    )
//                }
                UserAvatar(imageResId = R.drawable.avatar1)
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = user.name,
                    color = Color.LightGray,
                    fontSize = 26.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
//            CustomUserRow(
//                userName = user.name,
//                avatarResId = R.drawable.avatar1,
//            )

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
                            val intent = Intent(context, ChangePasswordActivity::class.java)
                            context.startActivity(intent)
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
            if (userProfileViewModel?.isAdminView == true) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomButton(
                        text = "REMOVE FROM GROUP",
                        onClick = { userProfileViewModel.leaveGroup(user.id) }
                    )
                }
            }
        }
    }
}

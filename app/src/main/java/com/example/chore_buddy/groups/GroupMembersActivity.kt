package com.example.chore_buddy.groups

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.R
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

import com.example.chore_buddy.components.UserRow
import com.example.chore_buddy.users.User
import com.example.chore_buddy.users.UserProfileActivity
import kotlinx.coroutines.launch

class GroupMembersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                GroupMembersScreen()
            }
        }
    }
}

@Composable
fun GroupMembersScreen() {
    val context = LocalContext.current

    val groupMembersViewModel: GroupMembersViewModel = viewModel()
    groupMembersViewModel.getCurrentGroupData()

    val group = groupMembersViewModel.group
    val members = groupMembersViewModel.members

    LaunchedEffect(groupMembersViewModel.errorMessage) {
        if (groupMembersViewModel.errorMessage != null) {
            Toast.makeText(
                context,
                groupMembersViewModel.errorMessage,
                Toast.LENGTH_LONG
            ).show()
            groupMembersViewModel.resetError()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                lifecycleOwner.lifecycleScope.launch {
                    if (!groupMembersViewModel.checkIfIsInGroup()) {
                        val activity = context as? ComponentActivity
                        activity?.finish()
                    }
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (group != null) {
        GroupMembersContent(
            group = group,
            users = members
        )
    } else {
        GroupMembersContent(
            group = Group(groupId = "There is no group"),
            emptyList()
        )
    }
}

@Preview(apiLevel = 34)
@Composable
fun GroupMembersPreview() {
    ChoreBuddyTheme {
        GroupMembersContent(
        Group(groupId = "123456"),
        listOf(
            User(
                id = "1",
                name = "User 1",
                groupId = "group2",
                email = "admin@example.com",
                role = "Admin",
            ),
            User(
                id = "2",
                name = "User 2",
                groupId = "group2",
                email = "jan@example.com",
                role = "User",
            ),
            User(
                id = "3",
                name = "User 3",
                groupId = "group2",
                email = "anna@example.com",
                role = "User",
            ),
            User(
                id = "4",
                name = "User 4",
                groupId = "group2",
                email = "piotr@example.com",
                role = "User",
            ),
            User(
                id = "5",
                name = "User 5",
                groupId = "group2",
                email = "maria@example.com",
                role = "User",
            )
        ))
    }
}

@Composable
fun GroupMembersContent(group: Group, users: List<User>?) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Group ID",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(fontFamily = interFontFamily)
        )
        Text(
            text = group.groupId,
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            style = TextStyle(fontFamily = interFontFamily)
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.White)
        users?.forEach { user ->
            UserRow(userName = user.name,
                onClick = {
                    val intent = Intent(context, UserProfileActivity::class.java)
                    intent.putExtra("USER_ID", user.id)
                    context.startActivity(intent)
                }
            )
            HorizontalDivider(thickness = 1.dp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}
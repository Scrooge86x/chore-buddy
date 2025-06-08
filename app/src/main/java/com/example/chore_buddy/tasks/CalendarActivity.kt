package com.example.chore_buddy.tasks

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.R
import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.groups.CreateOrJoinGroupActivity
import com.example.chore_buddy.groups.GroupMembersActivity
import com.example.chore_buddy.settings.MainSettingsActivity
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.users.UserProfileActivity

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
    val calendarViewModel : CalendarViewModel = viewModel()
    val groupText = if (calendarViewModel.isInGroup) "View Group Members" else "Create or Join Group"

    val context = LocalContext.current
    val systemBarPadding = WindowInsets.systemBars.asPaddingValues()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                calendarViewModel.checkIfInGroup()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(systemBarPadding)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chore_buddy_logo),
                    contentDescription = "Chore Buddy Logo",
                    modifier = Modifier
                        .height(80.dp)
                        .padding(start = 30.dp)
                        .size(100.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                NavbarMenu(items = listOf(
                    MenuItem(
                        text = "Settings",
                        onClick = {
                            val intent = Intent(context, MainSettingsActivity::class.java)
                            context.startActivity(intent)
                        },
                    ),
                    MenuItem(
                        text = "Check Your Profile",
                        onClick = {
                            val intent = Intent(context, UserProfileActivity::class.java)
                            intent.putExtra("USER_ID", AuthRepository.getCurrentUser()?.uid)
                            context.startActivity(intent)
                        },
                    ),
                    MenuItem(
                        text = groupText,
                        onClick = {
                            if (calendarViewModel.isInGroup) {
                                val intent = Intent(context, GroupMembersActivity::class.java)
                                context.startActivity(intent)
                            } else {
                                val intent = Intent(context, CreateOrJoinGroupActivity::class.java)
                                context.startActivity(intent)
                            }
                        },
                    ),
                    MenuItem(
                        text = "Create New Task",
                        onClick = {
                            val intent = Intent(context, CreateTaskActivity::class.java)
                            context.startActivity(intent)
                        },
                    ),
                ))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: Add a working DatePicker
        }
    }
}

data class MenuItem(
    val text: String,
    val onClick: () -> Unit,
)

@Composable
fun NavbarMenu(items: List<MenuItem>) {
    var isOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(48.dp)
    ) {
        IconButton(
            onClick = { isOpen = true },
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(36.dp),
                tint = Color.White
            )
        }
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = { isOpen = false },
            modifier = Modifier
                .width(192.dp)
                .background(Color.DarkGray),
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item.text,
                            color = Color.White
                        )
                    },
                    onClick = {
                        item.onClick()
                        isOpen = false
                    }
                )
                if (index < items.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

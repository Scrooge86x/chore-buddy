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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.auth.AuthRepository
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.groups.CreateOrJoinGroupActivity
import com.example.chore_buddy.groups.GroupMembersActivity
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
    val context = LocalContext.current

    val calendarViewModel : CalendarViewModel = viewModel()
    val groupText = if (calendarViewModel.isInGroup) "View group members" else "Create or Join group"

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
            text = "CHECK YOUR PROFILE",
            onClick = {
                val intent = Intent(context, UserProfileActivity::class.java)
                intent.putExtra("USER_ID", AuthRepository.getCurrentUser()?.uid)
                context.startActivity(intent)
            }
        )
        CustomButton(
            text = groupText,
            onClick = {
                if (calendarViewModel.isInGroup) {
                    val intent = Intent(context, GroupMembersActivity::class.java)
                    context.startActivity(intent)
                } else {
                    val intent = Intent(context, CreateOrJoinGroupActivity::class.java)
                    context.startActivity(intent)
                }
            }
        )
        ScreenHeading(text = "under construction")
    }
}

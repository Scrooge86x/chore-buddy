package com.example.chore_buddy.tasks

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.chore_buddy.components.CustomUserRow
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState
import com.example.chore_buddy.users.User
import com.example.chore_buddy.users.UserProfileActivity
import com.example.chore_buddy.users.UserProfileViewModel
import com.example.chore_buddy.users.UserRepository
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DayInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dateMillis = intent.getLongExtra("SELECTED_DATE", 0)
        val date = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                DayInfoScreen(date)
            }
        }
    }
}

@Composable
fun DayInfoScreen(date: LocalDate) {
    val dayInfoViewModel: DayInfoViewModel = viewModel()

    val context = LocalContext.current

    LaunchedEffect(dayInfoViewModel.errorMessage) {
        dayInfoViewModel.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            Log.e("error", it)
            dayInfoViewModel.resetError()
        }
    }

    dayInfoViewModel.getCurrentUser()
    val user = dayInfoViewModel.user

    if (user != null)
        dayInfoViewModel.getTasks(user.id, date.year, date.monthValue - 1, date.dayOfMonth)
    val tasks = dayInfoViewModel.tasks

    DayInfoContent(
        date = date,
        currentUser = user ?: User(
            name = "Invalid User",
            email = "Not registered yet",
            role = "Unassigned",
        ),
        tasks = tasks,
    )
}

@Composable
fun DayInfoContent(date: LocalDate, currentUser: User, tasks: List<Task>) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Logo()
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = date.format(formatter),
                    color = colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = colorScheme.onBackground)
            tasks.forEach { task ->
                if (task.assignedToId == currentUser.id) {
                    CustomUserRow(userName = task.assignedToName, avatarIcon = currentUser.avatarIcon)
                } else {
                    CustomUserRow(userName = task.assignedToName)
                }
                HorizontalDivider(thickness = 1.dp, color = colorScheme.onBackground)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        FloatingActionButton(
            onClick = { /* TODO: action */ },
            containerColor = colorScheme.onBackground,
            contentColor = colorScheme.background,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}

fun sampleTasks() = listOf(
    Task(title = "1 task title", description = "1 task description", assignedToId = "123", assignedToName = "user", createdBy = "321", groupId = "1234"),
    Task(title = "2 task title", description = "2 task description", assignedToId = "345", assignedToName = "user2", createdBy = "321", groupId = "1234"),
    Task(title = "3 task title", description = "3 task description", assignedToId = "123", assignedToName = "user3", createdBy = "321", groupId = "1234")
)

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun DayInfoPreviewLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        DayInfoContent(
            date = LocalDate.of(2000, 1, 1),
            currentUser = User(
                id = "345",
                name = "user2",
                avatarIcon = 2,
            ),
            tasks = sampleTasks(),
        )
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun DayInfoPreviewDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        DayInfoContent(
            date = LocalDate.of(2000, 1, 1),
            currentUser = User(
                id = "345",
                name = "user2",
                avatarIcon = 2,
            ),
            tasks = sampleTasks(),
        )
    }
}

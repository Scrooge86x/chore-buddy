package com.example.chore_buddy.tasks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserAvatar
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState
import com.example.chore_buddy.users.User
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DayInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                DayInfoScreen(intent.getLongExtra("SELECTED_DATE", 0))
            }
        }
    }
}

@Composable
fun DayInfoScreen(dateMillis: Long) {
    val dayInfoViewModel: DayInfoViewModel = viewModel()

    val context = LocalContext.current

    LaunchedEffect(dayInfoViewModel.errorMessage) {
        dayInfoViewModel.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            Log.e("error", it)
            dayInfoViewModel.resetError()
        }
    }

    val date = Instant.ofEpochMilli(dateMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    dayInfoViewModel.getTasks(date.year, date.monthValue - 1, date.dayOfMonth)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            dayInfoViewModel.getTasks(date.year, date.monthValue - 1, date.dayOfMonth)
        }
    }

    DayInfoContent(
        date = date,
        currentUser = dayInfoViewModel.user ?: User(
            name = "Invalid User",
            email = "Not registered yet",
            role = "Unassigned",
        ),
        tasks = dayInfoViewModel.tasks,
        onAddTaskClicked = {
            Intent(context, CreateTaskActivity::class.java).apply {
                putExtra("TASK_DATE", dateMillis)
                launcher.launch(this)
            }
        }
    )
}

@Composable
fun DayInfoContent(
    date: LocalDate,
    currentUser: User,
    tasks: List<Task>,
    onAddTaskClicked: () -> Unit = {},
) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val context = LocalContext.current

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
                TaskInfoRow(
                    task = task,
                    avatarIcon = if (task.assignedToId == currentUser.id) currentUser.avatarIcon else -1,
                    onClick = {
                        Intent(context, TaskDetailsActivity::class.java).apply {
                            putExtra("TASK_ID", task.id)
                            context.startActivity(this)
                        }
                    }
                )
                HorizontalDivider(thickness = 1.dp, color = colorScheme.onBackground)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        FloatingActionButton(
            onClick = onAddTaskClicked,
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

@Composable
fun TaskInfoRow(
    task: Task,
    avatarIcon: Int = -1,
    onClick: () -> Unit = {}
) {
    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(colorScheme.background)
            .clickable(onClick = onClick)
            .padding(horizontal = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        if (avatarIcon != -1) {
            UserAvatar(avatarIndex = avatarIcon)
        } else {
            Spacer(modifier = Modifier.width(56.dp))
        }
        Spacer(modifier = Modifier.width(24.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = task.assignedToName,
                fontSize = 16.sp,
                color = colorScheme.onBackground,
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = task.title,
                fontSize = 22.sp,
                color = colorScheme.onBackground,
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Medium
            )
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

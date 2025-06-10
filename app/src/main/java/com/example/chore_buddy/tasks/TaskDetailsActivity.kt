package com.example.chore_buddy.tasks

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.R
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.MultiLineInput
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState
import com.google.firebase.Timestamp
import java.util.Calendar

class TaskDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val taskId = intent.getStringExtra("TASK_ID")

        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                TaskDetailsScreen(taskId = taskId ?: "null")
            }
        }
    }
}

val interFontFamily = FontFamily(Font(R.font.inter_regular))

@Composable
fun TaskDetailsScreen(taskId: String) {
    val taskDetailsViewModel: TaskDetailsViewModel = viewModel()

    val context = LocalContext.current

    LaunchedEffect(taskDetailsViewModel.errorMessage) {
        taskDetailsViewModel.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            taskDetailsViewModel.resetError()
        }
    }

    LaunchedEffect(taskDetailsViewModel.successMessage) {
        taskDetailsViewModel.successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            taskDetailsViewModel.resetSuccessMessage()
        }
    }

    taskDetailsViewModel.getTask(taskId)
    taskDetailsViewModel.checkIfIsAdminOrSelf()

    val task = taskDetailsViewModel.task
    if (task != null) {
        TaskDetailsContent(
            task = task,
            isAdminOrSelf = taskDetailsViewModel.isAdminOrSelf,
            taskDetailsViewModel = taskDetailsViewModel,
        )
    } else {
        TaskDetailsContent(
            task = Task(title = "Invalid task, please report."),
            isAdminOrSelf = false,
        )
    }
}

@Composable
fun TaskDetailsContent(
    task: Task,
    isAdminOrSelf: Boolean,
    taskDetailsViewModel: TaskDetailsViewModel? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        ScreenHeading(text = task.title)
        Spacer(modifier = Modifier.height(72.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "Assigned to: ${task.assignedToName}",
                    color = colorScheme.onBackground,
                    fontSize = 18.sp,
                    style = TextStyle(fontFamily = interFontFamily),
                    modifier = Modifier.padding(bottom = 16.dp),
                )
                DateView(
                    label = "Creation Date",
                    date = task.createdAt.toDate().toString(),
                )
                DateView(
                    label = "Due Date",
                    date = task.dueDate.toDate().toString(),
                )
            }
            StatusCheckbox(
                isChecked = taskDetailsViewModel?.isChecked == true,
                isAdmin = isAdminOrSelf,
                onClick = { taskDetailsViewModel?.changeIsChecked(task.id) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorScheme.onBackground)
        )
        Spacer(modifier = Modifier.height(16.dp))
        MultiLineInput(
            label = "Task Description",
            value = taskDetailsViewModel?.description ?: "",
            onValueChange = { if (isAdminOrSelf && taskDetailsViewModel != null)
                taskDetailsViewModel.description = it },
            placeholderText = "Task details...",
            modifier = Modifier.padding(horizontal = 4.dp),
            isEnabled = isAdminOrSelf,
        )
        if (isAdminOrSelf) {
            Spacer(modifier = Modifier.height(48.dp))
            CustomButton(
                text = "SAVE",
                onClick = {
                    taskDetailsViewModel?.updateDescription(task.id)
                }
            )
        }
    }
}

@Composable
fun DateView(
    label: String = "",
    date: String = ""
) {
    Text(
        text = label,
        color = colorScheme.onBackground,
        fontSize = 18.sp,
        style = TextStyle(fontFamily = interFontFamily)
    )
    Text(
        text = date,
        color = colorScheme.onBackground,
        fontSize = 18.sp,
        style = TextStyle(fontFamily = interFontFamily),
        modifier = Modifier.padding(bottom = 16.dp)
    )
}


@Composable
fun StatusCheckbox(
    isChecked: Boolean,
    isAdmin: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Status",
            color = colorScheme.onBackground,
            fontSize = 16.sp,
            style = TextStyle(fontFamily = interFontFamily),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .size(64.dp)
                .border(
                    width = 2.dp,
                    color = colorScheme.onBackground,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            val checkedIcon = @Composable {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Checked",
                    tint = colorScheme.background,
                    modifier = Modifier
                        .background(colorScheme.onBackground, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
            when {
                isAdmin && isChecked -> IconButton(onClick = onClick) { checkedIcon() }
                isAdmin -> IconButton(onClick = onClick) {}
                isChecked -> checkedIcon()
            }
        }
    }
}

fun sampleTask() = Task(
    title = "Clean the dishes.",
    description = "Please do it carefully this time.",
    assignedToName = "John Doe",
    createdAt = Timestamp(Calendar.getInstance().apply {
        set(2025, 10, 20, 12, 44, 10)
    }.time),
    dueDate = Timestamp(Calendar.getInstance().apply {
        set(2025, 10, 25, 12, 0, 0)
    }.time),
)

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun TaskDetailsPreviewLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        TaskDetailsContent(
            task = sampleTask(),
            isAdminOrSelf = true,
        )
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun TaskDetailsPreviewDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        TaskDetailsContent(
            task = sampleTask(),
            isAdminOrSelf = true,
        )
    }
}

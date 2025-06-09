package com.example.chore_buddy.tasks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.MultiLineInput
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.components.TimeInputCustomDialog
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CreateTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dateMillis = intent.getLongExtra("TASK_DATE", 0)
        val instantDate = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()

        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                CreateTaskScreen(Date.from(instantDate))
            }
        }
    }
}

@Composable
fun CreateTaskScreen(date: Date) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val createTaskViewModel: CreateTaskViewModel = viewModel()
    createTaskViewModel.loadTaskData()

    LaunchedEffect(Unit) {
        createTaskViewModel.taskDueDate = date
    }

    LaunchedEffect(createTaskViewModel.wasSuccessful) {
        if (createTaskViewModel.wasSuccessful) {
            Toast.makeText(
                context,
                "Task was successfully created.",
                Toast.LENGTH_LONG
            ).show()
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra("USER_ID")?.let { value ->
                createTaskViewModel.taskAssignedToId = value
            }
            result.data?.getStringExtra("USER_NAME")?.let { value ->
                createTaskViewModel.taskAssignedToName = value
            }
        }
    }

    CreateTaskContent(
        taskTitle = createTaskViewModel.taskTitle,
        taskDescription = createTaskViewModel.taskDescription,
        assignedUser = createTaskViewModel.taskAssignedToName,
        createTaskViewModel = createTaskViewModel,
        assignMemberCallback = {
            val intent = Intent(context, AssignUserActivity::class.java)
            launcher.launch(intent)
        },
        createTaskCallback = {
            createTaskViewModel.createCurrentTask()
        },
        timeChosenCallback = { hour, minute ->
            createTaskViewModel.taskDueDate = Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
        }
    )
}

@Composable
fun CreateTaskContent(
    taskTitle: String = "",
    taskDescription: String = "",
    assignedUser: String? = null,
    assignMemberCallback: () -> Unit = {},
    createTaskCallback: () -> Unit = {},
    timeChosenCallback: (Int, Int) -> Unit = { hour, minute -> },
    createTaskViewModel: CreateTaskViewModel? = null,
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    var showDialog by remember { mutableStateOf(false) }
    var createButtonEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(createTaskViewModel?.errorMessage) {
        if (createTaskViewModel?.errorMessage != null) {
            Toast.makeText(
                context,
                createTaskViewModel.errorMessage,
                Toast.LENGTH_LONG
            ).show()
            createTaskViewModel.resetError()
            createButtonEnabled = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo(
            modifier = Modifier.offset(y = (-8).dp)
        )
        ScreenHeading(text = "New Task")
        Spacer(modifier = Modifier.height(10.dp))
        if (assignedUser != null) {
            Text(
                text = "Assigned user",
                color = colorScheme.onBackground,
                fontSize = 22.sp
            )
            Text(
                text = assignedUser,
                color = colorScheme.onBackground,
                fontSize = 20.sp
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            UserInput(
                label = "Task title",
                value = taskTitle,
                onValueChange = { createTaskViewModel?.taskTitle = it }
            )
            MultiLineInput(
                label = "Description",
                value = taskDescription,
                onValueChange = { createTaskViewModel?.taskDescription = it },
                modifier = Modifier.height(120.dp)
            )
        }
        Column {
            Spacer(modifier = Modifier.height(12.dp))
            createTaskViewModel?.let {
                Text(
                    text = "Due time: ${SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(it.taskDueDate)}",
                    color = colorScheme.onBackground,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
            CustomButton(text = "CHOOSE DUE TIME", onClick = { showDialog = true })
            if (showDialog) {
                TimeInputCustomDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = { hour, minute ->
                        timeChosenCallback(hour, minute)
                        showDialog = false
                    },
                    confirmText = "OK",
                    dismissText = "Cancel"
                )
            }
            if (createTaskViewModel?.taskGroupId != null) {
                Spacer(modifier = Modifier.height(12.dp))
                CustomButton(text = "ASSIGN MEMBER", onClick = assignMemberCallback)
            }
            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(
                text = "CREATE",
                enabled = createButtonEnabled,
                onClick = {
                    createButtonEnabled = false
                    createTaskCallback()
                },
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(text = "CANCEL", onClick = {
                activity?.finish()
            })
        }
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun CreateTaskPreviewLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        CreateTaskContent(
            taskTitle = "Clean the dishes",
            taskDescription = "Remember to use the new sponge",
            assignedUser = "John Doe"
        )
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun CreateTaskPreviewDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        CreateTaskContent(
            taskTitle = "Clean the dishes",
            taskDescription = "Remember to use the new sponge",
            assignedUser = "John Doe"
        )
    }
}

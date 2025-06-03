package com.example.chore_buddy.tasks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.components.MultiLineInput
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

class CreateTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                CreateTaskScreen()
            }
        }
    }
}

@Composable
fun CreateTaskScreen() {
    val context = LocalContext.current
    val createTaskViewModel : CreateTaskViewModel = viewModel()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra("USER_ID")?.let { value ->
                createTaskViewModel.assignedUserId = value
            }
        }
    }

    CreateTaskContent(
        task = createTaskViewModel.currentTask,
        assignedUser = createTaskViewModel.assignedUserId,
        assignMemberCallback = {
            val intent = Intent(context, AssignUserActivity::class.java)
            launcher.launch(intent)
        }
    )
}

@Composable
fun CreateTaskContent(
    task: Task,
    assignedUser: String?,
    assignMemberCallback: () -> Unit = {},
    createTaskCallback: () -> Unit = {}
) {
    val activity = LocalActivity.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(modifier = Modifier.height(16.dp))
        ScreenHeading(text = "New Task")
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            UserInput(
                label = "Task title",
                value = task.title,
                onValueChange = { task.title = it }
            )
            MultiLineInput(
                label = "Description",
                value = task.description,
                onValueChange = { task.description = it }
            )
        }
        // This is a TEMPORARY placeholder, integrate it in the UI so it looks nice
        Text(
            text = "Assigned user id: $assignedUser",
            color = Color.White,
            fontSize = 24.sp
        )
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            CustomButton(text = "ASSIGN MEMBER", onClick = assignMemberCallback)
            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(text = "CREATE", onClick = createTaskCallback)
            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(text = "CANCEL", onClick = {
                activity?.finish()
            })
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun CreateTaskPreview() {
    ChoreBuddyTheme {
        CreateTaskContent(
            task = Task(
                title = "Clean the dishes",
                description = "Remember to use the new sponge"
            ),
            assignedUser = "123456"
        )
    }
}

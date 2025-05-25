package com.example.chore_buddy.groups

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chore_buddy.R
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.UserInput
import com.example.chore_buddy.tasks.CalendarActivity
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

class CreateOrJoinGroupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                CreateOrJoinGroupScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun CreateOrJoinGroupScreen() {
    val createOrJoinGroupViewModel: CreateOrJoinGroupViewModel = viewModel()

    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    val context = LocalContext.current
    val activity = context as? ComponentActivity

    LaunchedEffect(createOrJoinGroupViewModel.errorMessage) {
        if (createOrJoinGroupViewModel.errorMessage != null) {
            Toast.makeText(context, createOrJoinGroupViewModel.errorMessage, Toast.LENGTH_LONG).show()
            createOrJoinGroupViewModel.resetError()
        }
    }

    LaunchedEffect(createOrJoinGroupViewModel.isSuccess) {
        if (createOrJoinGroupViewModel.isSuccess == CreateOrJoinGroupViewModel.Success.Created) {
            Toast.makeText(context, "Group successfully created", Toast.LENGTH_LONG).show()
        }
        if (createOrJoinGroupViewModel.isSuccess == CreateOrJoinGroupViewModel.Success.Joined) {
            Toast.makeText(context, "Successfully joined group", Toast.LENGTH_LONG).show()
        }
        if (createOrJoinGroupViewModel.isSuccess != CreateOrJoinGroupViewModel.Success.No) {
            val intent = Intent(context, CalendarActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
            activity?.finish()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Create Group",
            color = Color.LightGray,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(fontFamily = interFontFamily)
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserInput(
            label = "Group Name",
            value = createOrJoinGroupViewModel.groupName,
            onValueChange = { createOrJoinGroupViewModel.groupName = it }
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(text = "CREATE", onClick = {
            createOrJoinGroupViewModel.createGroup()
        })
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Join Group",
            color = Color.LightGray,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(fontFamily = interFontFamily)
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserInput(
            label = "Group Id",
            value = createOrJoinGroupViewModel.groupId,
            onValueChange = { createOrJoinGroupViewModel.groupId = it }
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(text = "JOIN", onClick = {
            createOrJoinGroupViewModel.joinGroup()
        })
        Spacer(modifier = Modifier.height(128.dp))
    }
}

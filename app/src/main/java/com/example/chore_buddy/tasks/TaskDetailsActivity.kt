package com.example.chore_buddy.tasks

import android.os.Bundle
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.MultiLineInput
import com.example.chore_buddy.components.ScreenHeading
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme
import com.example.chore_buddy.ui.theme.ThemeState

class TaskDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
                TaskDetailsScreen()
            }
        }
    }
}

val interFontFamily = FontFamily(Font(R.font.inter_regular))

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

@Composable
fun TaskDetailsScreen(
    isAdmin: Boolean = false
) {
    var isChecked by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

    val creationDate = "01/01/2000"
    val dueDate = "01/01/2001"

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
        ScreenHeading(text = "Task Title")
        Spacer(modifier = Modifier.height(72.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                DateView(
                    label = "Creation Date",
                    date = creationDate,
                )
                DateView(
                    label = "Due Date",
                    date = dueDate,
                )
            }
            StatusCheckbox(
                isChecked = isChecked,
                isAdmin = isAdmin,
                onClick = { isChecked = !isChecked }
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
            value = description,
            onValueChange = { if (isAdmin) description = it },
            placeholderText = "Task details...",
            modifier = Modifier.padding(horizontal = 4.dp),
            isEnabled = isAdmin,
        )
        if (isAdmin) {
            Spacer(modifier = Modifier.height(48.dp))
            CustomButton(
                text = "SAVE",
                onClick = {
                    // save logic here
                }
            )
        }
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun TaskDetailsPreviewLight() {
    ThemeState.isDarkTheme = false
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        TaskDetailsScreen()
    }
}

@Preview(apiLevel = 34, showBackground = true)
@Composable
fun TaskDetailsPreviewDark() {
    ThemeState.isDarkTheme = true
    ChoreBuddyTheme(darkTheme = ThemeState.isDarkTheme) {
        TaskDetailsScreen()
    }
}

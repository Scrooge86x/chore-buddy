package com.example.chore_buddy.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chore_buddy.R
import com.example.chore_buddy.components.CustomButton
import com.example.chore_buddy.components.Logo
import com.example.chore_buddy.components.MultiLineInput
import com.example.chore_buddy.ui.theme.ChoreBuddyTheme

class TaskDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoreBuddyTheme {
                TaskDetailsScreen()
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun TaskDetailsScreen(
    admin: Boolean = true
) {
    var isChecked by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

    val interFontFamily = FontFamily(Font(R.font.inter_regular))

    val creationDate = "01/01/2000"
    val dueDate = "01/01/2000"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Task Title",
            color = Color.LightGray,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(fontFamily = interFontFamily)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "Creation Date",
                    color = Color.White,
                    fontSize = 18.sp,
                    style = TextStyle(fontFamily = interFontFamily)
                )
                Text(
                    text = creationDate,
                    color = Color.LightGray,
                    fontSize = 18.sp,
                    style = TextStyle(fontFamily = interFontFamily)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Due Date",
                    color = Color.White,
                    fontSize = 18.sp,
                    style = TextStyle(fontFamily = interFontFamily)
                )
                Text(
                    text = dueDate,
                    color = Color.LightGray,
                    fontSize = 18.sp,
                    style = TextStyle(fontFamily = interFontFamily)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Status",
                    color = Color.White,
                    fontSize = 16.sp,
                    style = TextStyle(fontFamily = interFontFamily)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .border(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (admin) {
                        IconButton(onClick = { isChecked = !isChecked }) {
                            if (isChecked) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .background(Color.LightGray, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                                        .fillMaxSize()
                                        .padding(8.dp)
                                )
                            }
                        }
                    } else {
                        if (isChecked) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Checked",
                                tint = Color.Black,
                                modifier = Modifier
                                    .background(Color.LightGray, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                                    .fillMaxSize()
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(16.dp))
        MultiLineInput(
            label = "Task Description",
            value = description,
            onValueChange = { if (admin) description = it },
            placeholderText = "Task details...",
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        if (admin) {
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

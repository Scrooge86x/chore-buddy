package com.example.chore_buddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chore_buddy.R

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.chore_buddy_logo),
        contentDescription = "Chore Buddy Logo",
        modifier = Modifier
            .height(80.dp)
            .size(300.dp)
    )
}
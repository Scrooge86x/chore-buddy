package com.example.chore_buddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chore_buddy.R
import com.example.chore_buddy.ui.theme.ThemeState

@Composable
fun Logo(
    modifier: Modifier = Modifier
        .padding(top = 48.dp)
        .height(80.dp)
        .size(300.dp)
) {
    val logoRes = if (ThemeState.isDarkTheme) {
        R.drawable.chore_buddy_logo
    } else {
        R.drawable.chore_buddy_logo_light
    }

    Image(
        painter = painterResource(id = logoRes),
        contentDescription = "Chore Buddy Logo",
        modifier = modifier
    )
}

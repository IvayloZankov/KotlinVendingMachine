package com.fosents.kotlinvendingmachine.ui.screen.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.fosents.kotlinvendingmachine.ui.theme.BackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendingTopBar(
    title: String,
    iconResource: Int,
    iconDescription: String,
    onIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor,
        ),
        actions = {
            IconButton(onClick = onIconClick) {
                Icon(
                    painterResource(iconResource),
                    tint = Color.White,
                    contentDescription = iconDescription
                )
            }
        }
    )
}

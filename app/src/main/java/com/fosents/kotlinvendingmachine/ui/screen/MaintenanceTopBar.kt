package com.fosents.kotlinvendingmachine.ui.screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.ui.theme.Teal700

@Composable
fun MaintenanceTopBar(onIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White
            )
        },
        backgroundColor = Teal700,
        actions = {
            IconButton(onClick = onIconClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.back_button)
                )
            }
        })
}

@Composable
@Preview
fun MaintenanceTopBarPreview() {
    MaintenanceTopBar {}
}

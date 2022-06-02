package com.fosents.kotlinvendingmachine.ui.screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.ui.theme.Teal700

@Composable
fun ProductsTopBar(onMaintenanceClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White
            )
        },
        backgroundColor = Teal700,
        actions = {
            IconButton(onClick = onMaintenanceClicked) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.maintenance_button)
                )
            }
        })
}

@Composable
@Preview
fun ProductsTopBarPreview() {
    ProductsTopBar {

    }
}

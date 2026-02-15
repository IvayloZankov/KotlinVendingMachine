package com.fosents.kotlinvendingmachine.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.remote.utils.ExceptionHandler
import com.fosents.kotlinvendingmachine.sound.SoundManager
import com.fosents.kotlinvendingmachine.ui.alert.NoConnectionAlert
import com.fosents.kotlinvendingmachine.ui.theme.BackgroundColor
import com.fosents.kotlinvendingmachine.ui.theme.Gold
import com.fosents.kotlinvendingmachine.ui.theme.Teal700
import com.fosents.kotlinvendingmachine.ui.theme.Typography

@Composable
fun MaintenanceFragment(
    maintenanceViewModel: MaintenanceViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    MaintenanceScreen(
        onResetClick = {
            maintenanceViewModel.initReset(it)
        },
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceTopBar(onIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor,
        ),
        actions = {
            IconButton(onClick = onIconClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.back_button)
                )
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceScreen(
    onResetClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {}
) {

    val stateFlowError = ExceptionHandler.stateFlowError.collectAsState()
    val showErrorDialog = remember { mutableStateOf(false) }

    stateFlowError.value.getContentIfNotHandled()?.let {
        showErrorDialog.value = true
    }

    if (showErrorDialog.value) {
        NoConnectionAlert {
            showErrorDialog.value = false
        }
    }

    val options = listOf(
        R.string.maintenance_products_reset,
        R.string.maintenance_coins_reset
    )

    Scaffold(
        topBar = {
            MaintenanceTopBar {
                SoundManager.getInstance().playClick()
                onBackClick()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColor)
                .padding(paddingValues)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(all = 16.dp)
            ) {
                items(2) {
                    MaintenanceCard(options[it]) {
                        SoundManager.getInstance().playClick()
                        onResetClick(options[it])
                    }
                }
            }
        }
    }
}

@Composable
fun MaintenanceCard(
    resource: Int,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) Gold else Color.White
    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        elevation =  ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color),
        modifier = Modifier
            .padding(top = 20.dp, start = 5.dp, end = 5.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = resource),
                style = Typography.titleLarge,
                color = Teal700,
                modifier = Modifier.padding(5.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PreviewMaintenanceScreen() {
    MaintenanceScreen()
}

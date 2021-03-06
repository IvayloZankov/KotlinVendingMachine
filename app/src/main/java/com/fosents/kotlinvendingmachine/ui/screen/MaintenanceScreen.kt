package com.fosents.kotlinvendingmachine.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.ui.alert.NoConnectionAlert
import com.fosents.kotlinvendingmachine.ui.theme.Gold
import com.fosents.kotlinvendingmachine.ui.theme.Teal700
import com.fosents.kotlinvendingmachine.ui.theme.Typography
import com.fosents.kotlinvendingmachine.ui.theme.backgroundColor

@Composable
fun MaintenanceScreen(
    navController: NavHostController,
    maintenanceViewModel: MaintenanceViewModel = hiltViewModel()) {

    val noConnection = maintenanceViewModel.noConnection.collectAsState()

    val options = listOf(
        stringResource(id = R.string.maintenance_products_reset),
        stringResource(id = R.string.maintenance_coins_reset)
    )

    if (noConnection.value) {
        NoConnectionAlert {
            maintenanceViewModel.resetNoConnection()
        }
    }

    Scaffold(
        topBar = {
            MaintenanceTopBar {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.backgroundColor)
                .padding(paddingValues)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(all = 16.dp)
            ) {
                items(2) {
                    MaintenanceCard(options[it]) {
                        maintenanceViewModel.initReset(options[it])
                    }
                }
            }
        }
    }
}

@Composable
fun MaintenanceCard(
    option: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) Gold else Color.White
    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        elevation =  ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color),
        modifier = Modifier
            .padding(top = 20.dp, start = 5.dp, end = 5.dp)) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = option,
                style = Typography.h6,
                color = Teal700,
                modifier = Modifier.padding(5.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PreviewMaintenanceScreen() {
    MaintenanceScreen(rememberNavController())
}

@Preview
@Composable
fun PreviewMaintenanceCard() {
    MaintenanceCard(stringResource(id = R.string.maintenance_products_reset)) {}
}

package com.fosents.kotlinvendingmachine.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.remote.utils.ExceptionHandler
import com.fosents.kotlinvendingmachine.data.remote.utils.OneTimeEvent
import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.model.Product
import com.fosents.kotlinvendingmachine.sound.SoundManager
import com.fosents.kotlinvendingmachine.ui.alert.NoConnectionAlert
import com.fosents.kotlinvendingmachine.ui.alert.ShowGetProductAlert
import com.fosents.kotlinvendingmachine.ui.alert.ShowOrderCancelledAlert
import com.fosents.kotlinvendingmachine.ui.theme.*
import java.util.*

@Composable
fun CoinsFragment(
    coinsViewModel: CoinsViewModel = hiltViewModel(),
    onAlertOkClick: () -> Unit
) {

    val selectedProduct by coinsViewModel.stateFlowSelectedProduct.collectAsState()
    val coinsStorage by coinsViewModel.stateFlowCoinsStorage.collectAsState(initial = emptyList())

    val insertedAmount by coinsViewModel.stateFlowInsertedAmount.collectAsState()

    val priceMet by coinsViewModel.stateFlowPriceMet.collectAsState()
    val changeCalculated by coinsViewModel.stateFlowChangeCalculated.collectAsState()
    val orderCancelled by coinsViewModel.stateflowOrderCancelled.collectAsState()
    val stateFlowError by ExceptionHandler.stateFlowError.collectAsState()

    val listChange by coinsViewModel.stateFlowListChange.collectAsState()

    CoinsScreen(
        selectedProduct = selectedProduct,
        coinsStorage = coinsStorage,
        insertedAmount = insertedAmount,
        priceMet = priceMet,
        changeCalculated = changeCalculated,
        orderCancelled = orderCancelled,
        stateFlowError = stateFlowError,
        listChange = listChange,

        onOrderCancelledCLick = {
            coinsViewModel.cancelOrder()
        },
        onAddAllUserCoins = {
            coinsViewModel.addUserCoins()
        },
        onAddUserCoin = {
            coinsViewModel.addUserCoin(it)
        },
        onAlertOkClick = onAlertOkClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsTopBar(onIconClick: () -> Unit) {
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
fun CoinsScreen(
    selectedProduct: Product?,
    coinsStorage: List<Coin>,
    insertedAmount: String,
    priceMet: Boolean,
    changeCalculated: Boolean,
    orderCancelled: Boolean,
    stateFlowError: OneTimeEvent<Throwable>,
    listChange: List<Coin>,

    onOrderCancelledCLick: () -> Unit,
    onAddAllUserCoins: () -> Unit,
    onAddUserCoin: (Coin) -> Unit,
    onAlertOkClick: () -> Unit
) {

    var coinsAlpha by rememberSaveable { mutableFloatStateOf(1f) }

    val showDialog = remember { mutableStateOf(true) }
    val showErrorDialog = remember { mutableStateOf(false) }

    stateFlowError.getContentIfNotHandled()?.let {
        showErrorDialog.value = true
    }

    if (showErrorDialog.value) {
        NoConnectionAlert {
            showErrorDialog.value = false
        }
    }

    if (priceMet && !showErrorDialog.value) {
        coinsAlpha = 0.5f
        if (!changeCalculated)
            onAddAllUserCoins()
        else if (showDialog.value) {
            ShowGetProductAlert(listChange) {
                showDialog.value = false
                SoundManager.getInstance().playClick()
                onAlertOkClick()
            }
        }
    }

    if (orderCancelled)
        if (showDialog.value) {
            ShowOrderCancelledAlert(listChange) {
                showDialog.value = false
                SoundManager.getInstance().playClick()
                onAlertOkClick()
            }
        }

    BackHandler(enabled = true) {
        onOrderCancelledCLick()
    }

    Scaffold(
        topBar = {
            CoinsTopBar {
                onOrderCancelledCLick()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(color = BackgroundColor)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (priceMet) {
                coinsAlpha = 0.5f
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .alpha(coinsAlpha),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 32.dp),
            ) {
                item(
                    span = { GridItemSpan(2) }
                ) {
                    ProductInfoBox(selectedProduct, insertedAmount)
                }
                items(coinsStorage.size) {
                    CoinsCard(coinsStorage[it]) {
                        if (!priceMet) {
                            SoundManager.getInstance().playCoin()
                            onAddUserCoin(coinsStorage[it])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductInfoBox(product: Product?, insertedCoins: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            product?.name?.let {
                Text(
                    text = product.name,
                    style = Typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(0.5F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .width(100.dp),
                        text = stringResource(id = R.string.coinsInserted),
                        style = Typography.titleLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .width(100.dp)
                            .padding(vertical = 15.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(vertical = 5.dp),
                        text = insertedCoins,
                        style = Typography.headlineLarge,
                        color = BackgroundColor,
                        textAlign = TextAlign.Center,
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.5F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .width(100.dp),
                        text = stringResource(id = R.string.productPrice),
                        style = Typography.titleLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .width(100.dp)
                            .padding(vertical = 15.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(vertical = 5.dp),
                        text = String.format(Locale.CANADA, "%.2f", product?.price),
                        style = Typography.headlineLarge,
                        color = BackgroundColor,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
fun CoinsCard(
    coin: Coin,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            interactionSource = interactionSource,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 15.dp,
                disabledElevation = 0.dp
            ),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Gold
            ),
            modifier = Modifier
                .size(140.dp)
                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(
                text = String.format(Locale.CANADA, "%.2f", coin.price),
                style = Typography.headlineLarge,
                color = Color.White,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoinsScreenPreview() {
    val product = Product(1, "Coke", 2.5, 10)
    val coins = listOf(
        Coin(1, "Toonie", 2.0, 10),
        Coin(2, "Loonie", 1.0, 10),
        Coin(3, "Quarter", 0.25, 10)
    )
    val errorEvent = OneTimeEvent(RuntimeException())
    errorEvent.hasBeenHandled = true

    CoinsScreen(
        selectedProduct = product,
        coinsStorage = coins,
        insertedAmount = "1.00",
        priceMet = false,
        changeCalculated = false,
        orderCancelled = false,
        stateFlowError = errorEvent,
        listChange = emptyList(),
        onOrderCancelledCLick = {},
        onAddAllUserCoins = {},
        onAddUserCoin = {},
        onAlertOkClick = {}
    )
}

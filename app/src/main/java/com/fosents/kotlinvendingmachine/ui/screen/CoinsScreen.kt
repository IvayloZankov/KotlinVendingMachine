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
import androidx.compose.material.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.model.Product
import com.fosents.kotlinvendingmachine.ui.alert.NoConnectionAlert
import com.fosents.kotlinvendingmachine.ui.alert.ShowGetProductAlert
import com.fosents.kotlinvendingmachine.ui.alert.ShowOrderCancelledAlert
import com.fosents.kotlinvendingmachine.ui.theme.*
import java.util.*

@Composable
fun CoinsScreen(
    navHostController: NavHostController,
    coinsViewModel: CoinsViewModel = hiltViewModel()) {

    var coinsAlpha by rememberSaveable { mutableStateOf(1f) }

    val selectedProduct = coinsViewModel.selectedProduct.collectAsState()
    val coinsStorage = coinsViewModel.coinsStorage.collectAsState(initial = emptyList())

    val insertedAmount = coinsViewModel.insertedAmount.collectAsState()

    val priceMet = coinsViewModel.priceMet.collectAsState()
    val changeCalculated = coinsViewModel.changeCalculated.collectAsState()
    val orderCancelled = coinsViewModel.orderCancelled.collectAsState()
    val noConnection = coinsViewModel.noConnection.collectAsState()

    if (noConnection.value) {
        NoConnectionAlert {
            coinsViewModel.resetNoConnection()
        }
    } else if (priceMet.value) {
        coinsAlpha = 0.5f
        if (!changeCalculated.value) coinsViewModel.addUserCoins()
        else ShowGetProductAlert(coinsViewModel.coinsForReturn) { navHostController.popBackStack() }
    }

    if (orderCancelled.value)
        ShowOrderCancelledAlert(coinsViewModel.coinsForReturn) {
            navHostController.popBackStack()
        }

    BackHandler(enabled = true) {
        coinsViewModel.cancelOrder()
    }

    Scaffold(
        topBar = {
            CoinsTopBar {
                coinsViewModel.cancelOrder()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.backgroundColor)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (priceMet.value) {
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
                    ProductInfoBox(selectedProduct.value, insertedAmount.value)
                }
                items(coinsStorage.value.size) {
                    CoinsCard(coinsStorage.value[it]) {
                        coinsViewModel.addUserCoin(coinsStorage.value[it])
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
                    style = Typography.h5,
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
                        style = Typography.h6,
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
                        style = Typography.h4,
                        color = MaterialTheme.colors.backgroundColor,
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
                        style = Typography.h6,
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
                        style = Typography.h4,
                        color = MaterialTheme.colors.backgroundColor,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
fun CoinsGrid(coinsAlpha: Float, coins: List<Coin>, updateInsertedAmount: (coin: Coin) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier
            .alpha(coinsAlpha)
            .height(200.dp),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
    ) {
        items(coins.size) {
            CoinsCard(coins[it]) {
                updateInsertedAmount(coins[it])
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
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 15.dp,
                disabledElevation = 0.dp
            ),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Gold
            ),
            modifier = Modifier
                .size(100.dp)
                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(
                text = String.format(Locale.CANADA, "%.2f", coin.price),
                style = Typography.h6,
                color = Color.White,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun PreviewInfoBox() {
    ProductInfoBox(product = Product(1, "Water", 11.20, 4), "0.00")
}

@Preview
@Composable
fun PreviewCoinsBox() {
    CoinsGrid(1f, listOf(
        Coin(1, "five_cents", 0.05, 4),
        Coin(2, "fifty_cents", 0.50, 4),
        Coin(3, "one_eur", 1.00, 4),
        Coin(4, "two_eur", 2.00, 4)
    )) {}
}

@Preview
@Composable
fun PreviewCoinsCard() {
    CoinsCard(Coin(1, "five_cents", 0.05, 4)) {}
}

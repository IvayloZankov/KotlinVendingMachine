package com.fosents.kotlinvendingmachine.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.remote.utils.ExceptionHandler.stateFlowError
import com.fosents.kotlinvendingmachine.model.Product
import com.fosents.kotlinvendingmachine.navigation.Screen
import com.fosents.kotlinvendingmachine.sound.SoundManager
import com.fosents.kotlinvendingmachine.ui.alert.NoConnectionAlert
import com.fosents.kotlinvendingmachine.ui.alert.ShowOutOfOrderAlert
import com.fosents.kotlinvendingmachine.ui.theme.Gold
import com.fosents.kotlinvendingmachine.ui.theme.Teal700
import com.fosents.kotlinvendingmachine.ui.theme.Typography
import com.fosents.kotlinvendingmachine.ui.theme.BackgroundColor
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    navController: NavHostController,
    productsViewModel: ProductsViewModel = hiltViewModel()) {
    val showDialog = remember { mutableStateOf(true) }
    val showErrorDialog = remember { mutableStateOf(false) }

    val products by productsViewModel.getProducts.collectAsState(initial = emptyList())
    val outOfOrder = productsViewModel.outOfOrder.collectAsState()
    val stateFlowError = stateFlowError.collectAsState()
    val isLoading = productsViewModel.isLoading.collectAsState()

    productsViewModel.fetchCoins()

    stateFlowError.value.getContentIfNotHandled()?.let {
        showErrorDialog.value = true
    }

    if (showErrorDialog.value) {
        NoConnectionAlert {
            showErrorDialog.value = false
            productsViewModel.fetchRemoteData()
        }
    }

    if (outOfOrder.value) {
        if (showDialog.value) {
            ShowOutOfOrderAlert {
                showDialog.value = false
                navController.navigate(Screen.Maintenance.route)
            }
        }
    }

    Scaffold(
        topBar = {
            ProductsTopBar {
                SoundManager.getInstance().playClick()
                navController.navigate(Screen.Maintenance.route)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColor)
                .padding(paddingValues),
//            contentAlignment = Alignment.Center
        ) {
            if (isLoading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            } else if (products.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        style = Typography.displaySmall,
                        text = stringResource(id = R.string.alert_title_out_of_products),
                        modifier = Modifier
                            .padding(bottom = 10.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                ProductsGrid(navController, products)
            }
        }
    }
}

@Composable
fun ProductsGrid(
    navController: NavHostController,
    products: List<Product>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        items(products.size) {
            ProductCard(products[it]) {
                SoundManager.getInstance().playClick()
                navController.navigate(Screen.Coins.passProductId(products[it].id))
            }
        }
    }
}


@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    var selected by remember { mutableStateOf(false) }
    val color = if (selected) Gold else Color.White
    Button(
        onClick = {if (product.quantity > 0) {
            selected = true
            onClick()
        }},
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = product.name,
                style = Typography.titleLarge,
                color = Teal700,
                modifier = Modifier.padding(5.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = if (product.quantity > 0) String.format(Locale.CANADA, "%.2f", product.price) else stringResource(id = R.string.noQuantity).uppercase(),
                style = Typography.bodyLarge,
                color = Teal700,
                modifier = Modifier.padding(5.dp),
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun PreviewProductsGrid() {
    ProductsGrid(rememberNavController(), listOf(
        Product(1, "Water", 1.2, 4),
        Product(2, "Coca Cola", 0.9, 0),
        Product(3, "Mineral Water", 0.5, 4),
        Product(4, "Long name product", 0.7, 4)))
}

@Preview
@Composable
fun PreviewProductCard() {
    ProductCard(Product(1, "Water", 1.25, 4)) {}
}

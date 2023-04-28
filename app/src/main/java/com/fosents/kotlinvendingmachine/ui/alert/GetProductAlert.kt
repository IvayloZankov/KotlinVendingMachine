package com.fosents.kotlinvendingmachine.ui.alert

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.ui.theme.BackgroundColor
import com.fosents.kotlinvendingmachine.ui.theme.Typography

@Composable
fun ShowGetProductAlert(listCoins: List<Coin>, onClick: () -> Unit) {
    AnimatedAlert {
        ContentGetProduct(it, listCoins, onClick)
    }
}

@Composable
fun ContentGetProduct(anims: List<Float>, listCoins: List<Coin>, onClick: () -> Unit) {
    Dialog(
        onDismissRequest = {}
    ) {
        Surface(
            modifier = Modifier
                .alpha(alpha = anims[0]),
            shape = RoundedCornerShape(10),
            color = BackgroundColor
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    style = Typography.headlineSmall,
                    text = stringResource(id = R.string.alert_title_thank_you),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(alpha = anims[2]),
                    color = Color.White
                )
                Text(
                    style = Typography.labelLarge,
                    text = stringResource(id = R.string.alert_text_get_product),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(alpha = anims[2]),
                    color = Color.White
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(alpha = anims[2]),
                ) {
                    items(listCoins.size) {
                        CoinsCardReturn(listCoins[it])
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        elevation =  ButtonDefaults.buttonElevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        ),
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .alpha(alpha = anims[3]),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        onClick = onClick,
                    ) {
                        Text(
                            text = "OK",
                            color = BackgroundColor
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGetProductAlert() {
    ContentGetProduct(listOf(1f, 1f, 1f, 1f), listOf(
        Coin(
            id = 2,
            name = "twenty_cents",
            price = 0.20,
            quantity = 10
        ),
        Coin(
            id = 4,
            name = "one_eur",
            price = 1.00,
            quantity = 5
        ),
        Coin(
            id = 5,
            name = "two_eur",
            price = 2.00,
            quantity = 1
        )
    )) {}
}

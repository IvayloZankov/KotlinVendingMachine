package com.fosents.kotlinvendingmachine.ui.alert

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.fosents.kotlinvendingmachine.sound.SoundManager
import com.fosents.kotlinvendingmachine.ui.theme.Typography
import com.fosents.kotlinvendingmachine.ui.theme.backgroundColor

@Composable
fun ShowOrderCancelledAlert(listCoins: List<Coin>, onClick: () -> Unit) {
    SoundManager.getInstance().playError()
    AnimatedAlert {
        ContentOrderCancelled(it, listCoins, onClick)
    }
}

@Composable
fun ContentOrderCancelled(anims: List<Float>, listCoins: List<Coin>, onClick: () -> Unit) {
    Dialog(
        onDismissRequest = {}
    ) {
        Surface(
            modifier = Modifier
                .alpha(alpha = anims[0]),
            shape = RoundedCornerShape(10),
            color = MaterialTheme.colors.backgroundColor
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)

            ) {
                Text(
                    style = Typography.h5,
                    text = stringResource(id = R.string.alert_title_order_canceled),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(alpha = anims[1]),
                    color = Color.White
                )
                Text(
                    style = Typography.subtitle1,
                    text = stringResource(id = R.string.alert_text_get_coins),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(alpha = anims[2]),
                    color = Color.White
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(alpha = anims[2])
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
                        elevation =  ButtonDefaults.elevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        ),
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .alpha(alpha = anims[3]),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        ),
                        onClick = onClick,
                    ) {
                        Text(
                            text = "OK",
                            color = MaterialTheme.colors.backgroundColor
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderCancelledAlert() {
    ContentOrderCancelled(
        listOf(1f, 1f, 1f, 1f), listOf(
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
        ),
        Coin(
            id = 8,
            name = "two_eur",
            price = 4.00,
            quantity = 8
        )
    )) {}
}

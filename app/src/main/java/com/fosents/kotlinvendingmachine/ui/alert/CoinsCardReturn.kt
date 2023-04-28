package com.fosents.kotlinvendingmachine.ui.alert

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.ui.theme.Gold
import com.fosents.kotlinvendingmachine.ui.theme.Typography
import java.util.*

@Composable
fun CoinsCardReturn(
    coin: Coin
) {
    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .padding(5.dp)
                .background(color = Gold, shape = CircleShape)
                .border(BorderStroke(2.dp, Color.White), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = String.format(Locale.CANADA, "%.2f", coin.price),
                    style = Typography.titleLarge,
                    color = Color.White,
                    maxLines = 1,
                )
                Text(
                    text = coin.quantity.toString(),
                    style = Typography.bodyMedium,
                    color = Color.White,
                    maxLines = 1,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCoinsCardReturn() {
    CoinsCardReturn(
        coin = Coin(
            id = 2,
            name = "twenty_cents",
            price = 0.20,
            quantity = 10
        )
    )
}

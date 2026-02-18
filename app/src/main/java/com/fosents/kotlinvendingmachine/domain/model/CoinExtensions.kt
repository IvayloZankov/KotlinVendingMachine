package com.fosents.kotlinvendingmachine.domain.model

fun List<Coin>.insertCoin(coin: Coin): List<Coin> {
    val existingCoinIndex = this.indexOfFirst { it.id == coin.id }

    return if (existingCoinIndex != -1) {
        this.mapIndexed { index, item ->
            if (index == existingCoinIndex) {
                item.copy(quantity = item.quantity + 1)
            } else {
                item
            }
        }
    } else {
        this + coin.copy(quantity = 1)
    }
}

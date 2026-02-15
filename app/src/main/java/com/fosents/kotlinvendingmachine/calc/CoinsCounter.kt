package com.fosents.kotlinvendingmachine.calc

import com.fosents.kotlinvendingmachine.model.Coin
import java.math.BigDecimal

fun insertCoin(coin: Coin, list: List<Coin>): List<Coin> {

    for (index in 0 until list.size) {
        if (coin.id == list[index].id) {
            list[index].quantity = list[index].quantity.plus(1)
            return list
        }
    }
    coin.quantity = 1
    return list.plus(coin)
}

fun insertUserCoins(listUser: List<Coin>, listStorage: List<Coin>) {
    listUser.forEach { coinUser ->
        for (index in listStorage.indices) {
            if (coinUser.id == listStorage[index].id) {
                listStorage[index].quantity = listStorage[index].quantity.plus(coinUser.quantity)
                break
            }
        }
    }
}

fun getCoinsForReturn(
    insertedAmount: String,
    productPrice: Double,
    coinsStorage: List<Coin>
): List<Coin> {
    var change = BigDecimal(insertedAmount).subtract(BigDecimal(productPrice.toString()))
    var listForReturn = listOf<Coin>()

    while (change.toDouble() > 0.00) {
        for (i in coinsStorage.size - 1 downTo 0) {
            val coin = coinsStorage[i]
            if (coin.quantity > 0 &&
                coin.price <= change.toDouble()){
                coin.quantity = coin.quantity.minus(1)
                change = change.subtract(BigDecimal(coin.price.toString()))
                val coinChange = coin.copy(quantity = 1)
                listForReturn = insertCoin(coinChange, listForReturn)
                break
            }
        }
    }
    return listForReturn
}

package com.fosents.kotlinvendingmachine.calc

import com.fosents.kotlinvendingmachine.model.Coin
import java.math.BigDecimal

fun insertCoin(coin: Coin, list: MutableList<Coin>) {
    var found = false
    for (index in 0 until list.size) {
        if (coin.id == list[index].id) {
            list[index].quantity = list[index].quantity.plus(1)
            found = true
            break
        }
    }
    if (!found) {
        coin.quantity = 1
        list.add(coin)
    }
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
    val listCoins = mutableListOf<Coin>()

    while (change.toDouble() > 0.00) {
        for (i in coinsStorage.size - 1 downTo 0) {
            val coin = coinsStorage[i]
            if (coin.quantity > 0 &&
                coin.price <= change.toDouble()){
                coin.quantity = coin.quantity.minus(1)
                change = change.subtract(BigDecimal(coin.price.toString()))
                val coinChange = coin.copy(quantity = 1)
                insertCoin(coinChange, listCoins)
                break
            }
        }
    }
    return listCoins
}

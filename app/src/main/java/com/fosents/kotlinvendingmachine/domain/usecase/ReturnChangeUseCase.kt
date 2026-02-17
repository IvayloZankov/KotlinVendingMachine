package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.insertCoin
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Calculates and returns the change for a transaction, updating the machine's coin storage.
 */
class ReturnChangeUseCase @Inject constructor() {

    operator fun invoke(
        insertedAmount: String,
        productPrice: Double,
        coinsStorage: List<Coin>
    ): List<Coin> {
        var change = BigDecimal(insertedAmount).subtract(BigDecimal(productPrice.toString()))
        var listChange = listOf<Coin>()

        while (change.toDouble() > 0.00) {
            for (i in coinsStorage.size - 1 downTo 0) {
                val coin = coinsStorage[i]
                if (coin.quantity > 0 &&
                    coin.price <= change.toDouble()){
                    coin.quantity = coin.quantity.minus(1)
                    change = change.subtract(BigDecimal(coin.price.toString()))
                    val coinChange = coin.copy(quantity = 1)
                    listChange = listChange.insertCoin(coinChange)
                    break
                }
            }
        }
        return listChange
    }
}

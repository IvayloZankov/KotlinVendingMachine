package com.fosents.kotlinvendingmachine.domain.model

data class PurchaseResult(
    val updatedProduct: Product,
    val updatedCoins: List<Coin>,
    val change: List<Coin>
)
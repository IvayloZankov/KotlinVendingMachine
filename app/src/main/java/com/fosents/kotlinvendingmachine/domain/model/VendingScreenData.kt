package com.fosents.kotlinvendingmachine.domain.model

data class VendingScreenData(
    val selectedProduct: Product?,
    val coins: List<Coin>
)
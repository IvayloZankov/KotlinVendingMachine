package com.fosents.kotlinvendingmachine.model

data class Coin(
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

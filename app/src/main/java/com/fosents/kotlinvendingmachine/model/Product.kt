package com.fosents.kotlinvendingmachine.model

data class Product(
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

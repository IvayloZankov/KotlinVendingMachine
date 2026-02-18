package com.fosents.kotlinvendingmachine.domain.model

data class Product(
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

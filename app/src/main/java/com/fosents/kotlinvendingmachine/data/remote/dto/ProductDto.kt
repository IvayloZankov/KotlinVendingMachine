package com.fosents.kotlinvendingmachine.data.remote.dto

import com.fosents.kotlinvendingmachine.data.local.entity.ProductEntity
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

fun ProductDto.toEntity() = ProductEntity(
    id = id,
    name = name,
    price = price,
    quantity = quantity
)

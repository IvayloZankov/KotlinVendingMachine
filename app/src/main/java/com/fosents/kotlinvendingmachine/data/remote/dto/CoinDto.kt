package com.fosents.kotlinvendingmachine.data.remote.dto

import com.fosents.kotlinvendingmachine.data.local.entity.CoinEntity
import com.fosents.kotlinvendingmachine.model.Coin
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

fun CoinDto.toEntity() = CoinEntity(
    id = id,
    name = name,
    price = price,
    quantity = quantity
)

fun Coin.toDto() = CoinDto(
    id = id,
    name = name,
    price = price,
    quantity = quantity
)

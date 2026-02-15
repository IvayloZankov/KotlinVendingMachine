package com.fosents.kotlinvendingmachine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fosents.kotlinvendingmachine.model.Coin
import kotlinx.serialization.Serializable

private const val COIN_DATABASE_TABLE = "coin_table"

@Serializable
@Entity(tableName = COIN_DATABASE_TABLE)
data class CoinEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

fun CoinEntity.toDomain() = Coin(
    id = id,
    name = name,
    price = price,
    quantity = quantity
)

fun Coin.toEntity() = CoinEntity(
    id = id,
    name = name,
    price = price,
    quantity = quantity
)

package com.fosents.kotlinvendingmachine.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fosents.kotlinvendingmachine.util.Constants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Constants.COIN_DATABASE_TABLE)
data class Coin(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

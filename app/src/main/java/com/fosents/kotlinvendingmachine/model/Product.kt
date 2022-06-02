package com.fosents.kotlinvendingmachine.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fosents.kotlinvendingmachine.util.Constants.PRODUCT_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = PRODUCT_DATABASE_TABLE)
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

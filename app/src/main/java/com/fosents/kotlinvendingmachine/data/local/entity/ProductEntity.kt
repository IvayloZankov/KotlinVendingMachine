package com.fosents.kotlinvendingmachine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fosents.kotlinvendingmachine.model.Product
import kotlinx.serialization.Serializable

private const val PRODUCT_DATABASE_TABLE = "product_table"

@Serializable
@Entity(tableName = PRODUCT_DATABASE_TABLE)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int
)

fun ProductEntity.toDomain() = Product(
    id = id,
    name = name,
    price = price,
    quantity = quantity
)

fun Product.toEntity() = ProductEntity(
    id = id,
    name = name,
    price = price,
    quantity = quantity
)

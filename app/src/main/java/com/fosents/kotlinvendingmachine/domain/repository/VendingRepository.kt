package com.fosents.kotlinvendingmachine.domain.repository

import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface VendingRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun getSelectedProduct(productId: Int): Product
    suspend fun getCoins(): List<Coin>
    suspend fun fetchRemoteData()
    suspend fun updateCoins(list: List<Coin>)
    suspend fun updateProduct(product: Product)
    suspend fun resetProducts()
    suspend fun resetCoins()
}

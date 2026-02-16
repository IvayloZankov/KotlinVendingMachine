package com.fosents.kotlinvendingmachine.data

import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product

interface RemoteDataSource {
    suspend fun fetchRemoteData()
    suspend fun getProducts(): List<Product>
    suspend fun getSelectedProduct(productId: Int): Product
    suspend fun updateProduct(product: Product)
    suspend fun resetProducts()
    suspend fun getCoins(): List<Coin>
    suspend fun updateCoins(list: List<Coin>)
    suspend fun resetCoins()
}
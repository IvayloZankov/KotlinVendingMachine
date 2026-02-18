package com.fosents.kotlinvendingmachine.domain.repository

import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product

class FakeVendingRepository : VendingRepository {
    var products = emptyList<Product>()
    var coins = emptyList<Coin>()
    var remoteDataFetched = false
    var productsReset = false
    var coinsReset = false

    override suspend fun getProducts(): List<Product> = products
    override suspend fun getSelectedProduct(productId: Int): Product = products.first { it.id == productId }
    override suspend fun getCoins(): List<Coin> = coins
    override suspend fun fetchRemoteData() { remoteDataFetched = true }
    override suspend fun updateCoins(list: List<Coin>) { coins = list.toMutableList() }
    override suspend fun updateProduct(product: Product) {
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) products = products.toMutableList().also { it[index] = product }
    }
    override suspend fun resetProducts() { productsReset = true }
    override suspend fun resetCoins() { coinsReset = true }
}

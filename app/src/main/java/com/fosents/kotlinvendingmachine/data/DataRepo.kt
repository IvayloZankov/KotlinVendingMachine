package com.fosents.kotlinvendingmachine.data

import com.fosents.kotlinvendingmachine.data.local.DataStoreOperations
import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepo @Inject constructor(
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
){

    suspend fun generateVendingId() {
        dataStore.generateVendingId()
    }

    fun readVendingId(): Flow<String> {
        return dataStore.readVendingId()
    }

    suspend fun fetchRemoteData() {
        remote.fetchRemoteData()
    }

    fun getProducts(): Flow<List<Product>> {
        return remote.getProducts()
    }

    suspend fun getSelectedProduct(productId: Int): Product {
        return remote.getSelectedProduct(productId)
    }

    suspend fun updateProduct(product: Product) {
        remote.updateProduct(product)
    }

    suspend fun resetProducts() {
        remote.resetProducts()
    }

    suspend fun getCoins(): List<Coin> {
        return remote.getCoins()
    }

    suspend fun updateCoins(list: List<Coin>) {
        remote.updateCoins(list)
    }

    suspend fun resetCoins() {
        remote.resetCoins()
    }
}
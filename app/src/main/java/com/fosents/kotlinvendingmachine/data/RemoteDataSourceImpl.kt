package com.fosents.kotlinvendingmachine.data

import com.fosents.kotlinvendingmachine.data.local.VendingDatabase
import com.fosents.kotlinvendingmachine.data.mediator.VendingMediator
import com.fosents.kotlinvendingmachine.data.remote.VendingApi
import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.model.Product
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    vendingApi: VendingApi,
    vendingDatabase: VendingDatabase
): RemoteDataSource {

    private val vendingMediator = VendingMediator(vendingApi, vendingDatabase)
    private val productsDao = vendingDatabase.productDao()
    private val coinsDao = vendingDatabase.coinDao()

    override suspend fun fetchRemoteData() {
        vendingMediator.fetchRemoteData()
    }

    override fun getProducts(): Flow<List<Product>> {
        return productsDao.getProducts()
    }

    override suspend fun getSelectedProduct(productId: Int): Product {
        return productsDao.getSelectedProduct(productId)
    }

    override suspend fun updateProduct(product: Product) {
        vendingMediator.updateProduct(product)
    }

    override suspend fun resetProducts() {
        vendingMediator.resetProducts()
    }

    override suspend fun getCoins(): List<Coin> {
        return coinsDao.getCoins()
    }

    override suspend fun updateCoins(list: List<Coin>) {
        vendingMediator.updateCoins(list)
    }

    override suspend fun resetCoins() {
        vendingMediator.resetCoins()
    }
}
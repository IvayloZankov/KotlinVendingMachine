package com.fosents.kotlinvendingmachine.data

import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.model.Product

class FakeRemoteDataSourceImpl: RemoteDataSource {

    override suspend fun fetchRemoteData() {

    }

    override suspend fun getProducts(): List<Product> {
        return listOf(
            Product(1, "Water", 1.2, 4),
            Product(2, "Coca Cola", 0.9, 0),
            Product(3, "Mineral Water", 0.5, 4),
            Product(4, "Long name product", 0.7, 4),
            Product(4, "Beans", 0.8, 6),
            Product(4, "Snacks", 0.5, 10),
            Product(4, "Ring", 5.0, 1)
        )
    }

    override suspend fun getSelectedProduct(productId: Int): Product {
        return Product(0, "", 0.50, 0)
    }

    override suspend fun updateProduct(product: Product) {

    }

    override suspend fun resetProducts() {

    }

    override suspend fun getCoins(): List<Coin> {
        return listOf(
            Coin(1, "five_cents", 0.05, 40),
            Coin(2, "fifty_cents", 0.50, 15),
            Coin(3, "one_eur", 1.00, 4),
            Coin(4, "two_eur", 2.00, 4)
        )
    }

    override suspend fun updateCoins(list: List<Coin>) {

    }

    override suspend fun resetCoins() {

    }
}
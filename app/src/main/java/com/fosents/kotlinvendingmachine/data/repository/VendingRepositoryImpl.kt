package com.fosents.kotlinvendingmachine.data.repository

import androidx.room.withTransaction
import com.fosents.kotlinvendingmachine.data.local.VendingDatabase
import com.fosents.kotlinvendingmachine.data.local.entity.toDomain
import com.fosents.kotlinvendingmachine.data.local.entity.toEntity
import com.fosents.kotlinvendingmachine.data.remote.VendingApi
import com.fosents.kotlinvendingmachine.data.remote.dto.toDto
import com.fosents.kotlinvendingmachine.data.remote.dto.toEntity
import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import javax.inject.Inject

class VendingRepositoryImpl @Inject constructor(
    private val api: VendingApi,
    private val database: VendingDatabase
): VendingRepository {
    private val productsDao = database.productDao()
    private val coinsDao = database.coinDao()

    override suspend fun getProducts(): List<Product> {
        return productsDao.getProducts().map { it.toDomain() }
    }

    override suspend fun getSelectedProduct(productId: Int): Product {
        return productsDao.getSelectedProduct(productId).toDomain()
    }

    override suspend fun getCoins(): List<Coin> {
        return coinsDao.getCoins().map { it.toDomain() }
    }

    override suspend fun fetchRemoteData() {
        val responseProducts = api.getProducts()
        if (responseProducts.data.isNotEmpty()) {
            database.withTransaction {
                productsDao.deleteAllProducts()
                productsDao.addProducts(responseProducts.data.map { it.toEntity() })
            }
        }
        val responseCoins = api.getCoins()
        if (responseCoins.data.isNotEmpty()) {
            database.withTransaction {
                coinsDao.deleteAllCoins()
                coinsDao.addCoins(responseCoins.data.map { it.toEntity() })
            }
        }
    }

    override suspend fun updateCoins(list: List<Coin>) {
        api.updateCoins(list.map { it.toDto() })
        database.withTransaction {
            coinsDao.deleteAllCoins()
            coinsDao.addCoins(list.map { it.toEntity() })
        }
    }

    override suspend fun updateProduct(product: Product) {
        api.decreaseProduct(mapOf("id" to product.id))
        productsDao.updateProduct(product.toEntity())
    }

    override suspend fun resetProducts() {
        val response = api.resetProducts()
        if (response.data.isNotEmpty()) {
            database.withTransaction {
                productsDao.deleteAllProducts()
                productsDao.addProducts(response.data.map { it.toEntity() })
            }
        }
    }

    override suspend fun resetCoins() {
        val response = api.resetCoins()
        if (response.data.isNotEmpty()) {
            database.withTransaction {
                coinsDao.deleteAllCoins()
                coinsDao.addCoins(response.data.map { it.toEntity() })
            }
        }
    }
}
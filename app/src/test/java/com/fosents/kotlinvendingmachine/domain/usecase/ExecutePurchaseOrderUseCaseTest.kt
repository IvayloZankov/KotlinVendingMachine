package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.repository.FakeVendingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ExecutePurchaseOrderUseCaseTest {

    private val repository = FakeVendingRepository()
    private val insertUserCoinsUseCase = InsertUserCoinsUseCase()
    private val returnChangeUseCase = ReturnChangeUseCase()
    private val useCase = ExecutePurchaseOrderUseCase(
        repository,
        insertUserCoinsUseCase,
        returnChangeUseCase
    )

    @Test
    fun `invoke should update product quantity and coins and return result`() = runBlocking {

        val initialProduct = Product(1, "Soda", 1.50, 5)
        val initialCoins = listOf(
            Coin(1, "Toonie", 2.0, 10),
            Coin(2, "Loonie", 1.0, 10),
            Coin(3, "Quarter", 0.25, 10)
        )
        repository.products = listOf(initialProduct)
        repository.coins = initialCoins

        val userInsertedCoins = listOf(
            Coin(1, "Toonie", 2.0, 1)
        )
        val totalInsertedAmount = "2.00"

        val result = useCase(
            initialProduct,
            initialCoins,
            userInsertedCoins,
            totalInsertedAmount
        )

        assertEquals(4, result.updatedProduct.quantity)

        val repoProduct = repository.getProducts().find { it.id == 1 }
        assertEquals(4, repoProduct?.quantity)
        
        val repoCoins = repository.getCoins()
        val toonies = repoCoins.find { it.name == "Toonie" }
        val quarters = repoCoins.find { it.name == "Quarter" }
        
        assertEquals(11, toonies?.quantity)
        assertEquals(8, quarters?.quantity)

        assertEquals(2, result.change.sumOf { it.quantity })
        assertEquals("Quarter", result.change[0].name)
    }
}

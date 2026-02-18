package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.repository.FakeVendingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductAndCoinsUseCaseTest {

    private val repository = FakeVendingRepository()
    private val useCase = GetProductAndCoinsUseCase(repository)

    @Test
    fun `invoke should return product and coins`() = runBlocking {
        repository.products = mutableListOf(
            Product(1, "Prod1", 1.0, 10),
            Product(2, "Prod2", 2.0, 5)
        )
        repository.coins = mutableListOf(
            Coin(1, "Coin1", 1.0, 10)
        )

        val result = useCase(2)

        assertEquals("Prod2", result.selectedProduct?.name)
        assertEquals(1, result.coins.size)
    }
}

package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.repository.FakeVendingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CheckOutOfOrderUseCaseTest {

    private val repository = FakeVendingRepository()
    private val useCase = CheckOutOfOrderUseCase(repository)

    @Test
    fun `invoke should return true when coins list is empty`() = runBlocking {
        repository.coins = emptyList()

        val result = useCase()

        assertTrue(result)
    }

    @Test
    fun `invoke should return true when first coin quantity is less than 40`() = runBlocking {
        repository.coins = mutableListOf(
            Coin(1, "Toonie", 2.0, 39)
        )

        val result = useCase()

        assertTrue(result)
    }

    @Test
    fun `invoke should return false when first coin quantity is 40 or more`() = runBlocking {
        repository.coins = mutableListOf(
            Coin(1, "Toonie", 2.0, 40)
        )

        val result = useCase()

        assertFalse(result)
    }
}

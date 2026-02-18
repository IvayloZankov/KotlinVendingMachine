package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.FakeVendingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class ResetProductsUseCaseTest {

    private val repository = FakeVendingRepository()
    private val useCase = ResetProductsUseCase(repository)

    @Test
    fun `invoke should reset products`() = runBlocking {
        repository.productsReset = false

        useCase()

        assertTrue(repository.productsReset)
    }
}

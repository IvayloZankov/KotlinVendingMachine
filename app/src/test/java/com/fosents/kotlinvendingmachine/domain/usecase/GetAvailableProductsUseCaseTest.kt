package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.repository.FakeVendingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAvailableProductsUseCaseTest {

    private val repository = FakeVendingRepository()
    private val useCase = GetAvailableProductsUseCase(repository)

    @Test
    fun `invoke should return only available products`() = runBlocking {
        repository.products = mutableListOf(
            Product(1, "Available", 1.0, 10),
            Product(2, "Out of Stock", 1.0, 0)
        )

        val result = useCase()

        assertEquals(1, result.size)
        assertEquals("Available", result[0].name)
    }
}

package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.FakeVendingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class ResetCoinsUseCaseTest {

    private val repository = FakeVendingRepository()
    private val useCase = ResetCoinsUseCase(repository)

    @Test
    fun `invoke should reset coins`() = runBlocking {
        repository.coinsReset = false

        useCase()

        assertTrue(repository.coinsReset)
    }
}

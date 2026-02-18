package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.FakeSettingsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ManageVendingIdUseCaseTest {

    private val repository = FakeSettingsRepository()
    private val useCase = ManageVendingIdUseCase(repository)

    @Test
    fun `invoke should generate vending ID if current ID is empty`() = runBlocking {
        repository.vendingId = ""
        repository.generated = false

        useCase()

        assertTrue(repository.generated)
    }

    @Test
    fun `invoke should not generate vending ID if current ID is not empty`() = runBlocking {
        repository.vendingId = "existing-id"
        repository.generated = false

        useCase()

        assertFalse(repository.generated)
    }
}

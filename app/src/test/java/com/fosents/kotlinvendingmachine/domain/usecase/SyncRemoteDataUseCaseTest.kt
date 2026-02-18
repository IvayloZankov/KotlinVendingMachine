package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.FakeVendingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class SyncRemoteDataUseCaseTest {

    private val repository = FakeVendingRepository()
    private val useCase = SyncRemoteDataUseCase(repository)

    @Test
    fun `invoke should sync remote data`() = runBlocking {
        repository.remoteDataFetched = false

        useCase()

        assertTrue(repository.remoteDataFetched)
    }
}

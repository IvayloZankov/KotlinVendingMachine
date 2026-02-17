package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import javax.inject.Inject

/**
 * Use case to sync remote data from the server.
 */
class SyncRemoteDataUseCase @Inject constructor(
    private val repository: VendingRepository
) {
    suspend operator fun invoke() {
        repository.fetchRemoteData()
    }
}
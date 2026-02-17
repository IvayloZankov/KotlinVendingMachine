package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import javax.inject.Inject

/**
 * Use case to reset the coins in the vending machine.
 */
class ResetCoinsUseCase @Inject constructor(
    private val repository: VendingRepository
) {
    suspend operator fun invoke() {
        repository.resetCoins()
    }
}
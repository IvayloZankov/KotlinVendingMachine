package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import javax.inject.Inject

/**
 * Use case to check if the vending machine is out of order if there is not
 * enough coins in the storage
 */
class CheckOutOfOrderUseCase @Inject constructor(
    private val repository: VendingRepository
) {
    suspend operator fun invoke(): Boolean {
        val coins = repository.getCoins()
        return if (coins.isNotEmpty()) {
            coins[0].quantity < 40
        } else {
            true
        }
    }
}

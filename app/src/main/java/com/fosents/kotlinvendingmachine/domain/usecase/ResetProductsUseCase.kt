package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import javax.inject.Inject

/**
 * Use case to reset the products in the vending machine.
 */
class ResetProductsUseCase @Inject constructor(
    private val repository: VendingRepository
) {
    suspend operator fun invoke() {
        repository.resetProducts()
    }
}
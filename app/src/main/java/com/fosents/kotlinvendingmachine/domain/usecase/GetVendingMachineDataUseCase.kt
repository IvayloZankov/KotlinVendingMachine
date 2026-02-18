package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.VendingScreenData
import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import javax.inject.Inject

/**
 * Use case to get the vending machine products and coins.
 */
class GetVendingMachineDataUseCase @Inject constructor(
    private val repository: VendingRepository
) {
    suspend operator fun invoke(productId: Int): VendingScreenData {
        val product = repository.getSelectedProduct(productId)
        val coins = repository.getCoins()
        return VendingScreenData(product, coins)
    }
}

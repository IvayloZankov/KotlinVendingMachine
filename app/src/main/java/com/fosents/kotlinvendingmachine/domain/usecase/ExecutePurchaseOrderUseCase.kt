package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.model.PurchaseResult
import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import javax.inject.Inject

/**
 * Use case to execute a purchase order.
 * Removes the product from the vending machine, inserts user coins, and returns change.
 */
class ExecutePurchaseOrderUseCase @Inject constructor(
    private val repository: VendingRepository,
    private val insertUserCoinsUseCase: InsertUserCoinsUseCase,
    private val returnChangeUseCase: ReturnChangeUseCase
) {
    suspend operator fun invoke(
        product: Product,
        currentStorageCoins: List<Coin>,
        userInsertedCoins: List<Coin>,
        totalInsertedAmount: String
    ): PurchaseResult {

        val updatedProduct = product.copy(quantity = product.quantity - 1)

        val coinsWithUserDeposit = insertUserCoinsUseCase(
            listUser = userInsertedCoins,
            listStorage = currentStorageCoins
        )

        val change = returnChangeUseCase(
            totalInsertedAmount,
            product.price,
            coinsWithUserDeposit
        )

        repository.updateProduct(updatedProduct)
        repository.updateCoins(coinsWithUserDeposit)

        return PurchaseResult(
            updatedProduct = updatedProduct,
            updatedCoins = coinsWithUserDeposit,
            change = change
        )
    }
}

package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case to get available products from the repository
 */
class GetAvailableProductsUseCase @Inject constructor(
    private val repository: VendingRepository
) {
    suspend operator fun invoke(): List<Product> {
        val products = repository.getProducts()
        return withContext(Dispatchers.Default) {
            products.filter { it.quantity > 0 }
        }
    }
}

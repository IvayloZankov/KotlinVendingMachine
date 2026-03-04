package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case to get available products from the repository
 */
class GetAvailableProductsUseCase @Inject constructor(
    private val repository: VendingRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getProducts()
            .map { products ->
                products.filter { product -> product.quantity > 0 }
            }
            .flowOn(Dispatchers.Default)
    }
}

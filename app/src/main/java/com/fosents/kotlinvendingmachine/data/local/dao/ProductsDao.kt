package com.fosents.kotlinvendingmachine.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.fosents.kotlinvendingmachine.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun getProductsFlow(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product_table WHERE id=:productId")
    suspend fun getSelectedProduct(productId: Int): ProductEntity

    @Insert(onConflict = REPLACE)
    suspend fun addProducts(products: List<ProductEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun updateProduct(product: ProductEntity)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()
}
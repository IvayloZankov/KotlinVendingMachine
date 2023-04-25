package com.fosents.kotlinvendingmachine.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.fosents.kotlinvendingmachine.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE id=:productId")
    suspend fun getSelectedProduct(productId: Int): Product

    @Insert(onConflict = REPLACE)
    suspend fun addProducts(products: List<Product>)

    @Insert(onConflict = REPLACE)
    suspend fun updateProduct(product: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()
}
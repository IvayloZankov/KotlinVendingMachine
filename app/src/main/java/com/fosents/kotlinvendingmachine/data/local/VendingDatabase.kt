package com.fosents.kotlinvendingmachine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fosents.kotlinvendingmachine.data.local.dao.CoinsDao
import com.fosents.kotlinvendingmachine.data.local.dao.ProductsDao
import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.model.Product

@Database(entities = [Product::class, Coin::class], version = 1, exportSchema = false)
abstract class VendingDatabase: RoomDatabase() {
    abstract fun productDao(): ProductsDao
    abstract fun coinDao(): CoinsDao
}
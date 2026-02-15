package com.fosents.kotlinvendingmachine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fosents.kotlinvendingmachine.data.local.VendingDatabase.Companion.DATABASE_VERSION
import com.fosents.kotlinvendingmachine.data.local.dao.CoinsDao
import com.fosents.kotlinvendingmachine.data.local.dao.ProductsDao
import com.fosents.kotlinvendingmachine.data.local.entity.CoinEntity
import com.fosents.kotlinvendingmachine.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, CoinEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false)
abstract class VendingDatabase: RoomDatabase() {
    abstract fun productDao(): ProductsDao
    abstract fun coinDao(): CoinsDao

    companion object {
        const val DATABASE_NAME = "vending_database"
        const val DATABASE_VERSION_OLD = 1
        const val DATABASE_VERSION = 2

        val MIGRATION = object : Migration(
            DATABASE_VERSION_OLD,
            DATABASE_VERSION
        ) {
            override fun migrate(db: SupportSQLiteDatabase) {
                //empty just to avoid error
            }
        }
    }
}

package com.fosents.kotlinvendingmachine.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.fosents.kotlinvendingmachine.data.local.entity.CoinEntity

@Dao
interface CoinsDao {

    @Query("SELECT * FROM coin_table ORDER BY id ASC")
    suspend fun getCoins(): List<CoinEntity>

    @Insert(onConflict = REPLACE)
    suspend fun addCoins(product: List<CoinEntity>)

    @Query("DELETE FROM coin_table")
    suspend fun deleteAllCoins()
}
package com.fosents.kotlinvendingmachine.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.fosents.kotlinvendingmachine.model.Coin

@Dao
interface CoinsDao {

    @Query("SELECT * FROM coin_table ORDER BY id ASC")
    suspend fun getCoins(): List<Coin>

    @Insert(onConflict = REPLACE)
    suspend fun addCoins(product: List<Coin>)

    @Query("DELETE FROM coin_table")
    suspend fun deleteAllCoins()
}
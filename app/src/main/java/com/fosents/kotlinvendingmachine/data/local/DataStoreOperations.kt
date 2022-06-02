package com.fosents.kotlinvendingmachine.data.local

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun generateVendingId()

    fun readVendingId(): Flow<String>
}
package com.fosents.kotlinvendingmachine.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataStoreOperations: DataStoreOperations {
    override suspend fun generateVendingId() {

    }

    override fun readVendingId(): Flow<String> {
        return flow {  }
    }
}
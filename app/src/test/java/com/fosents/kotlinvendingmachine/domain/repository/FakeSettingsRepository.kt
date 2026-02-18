package com.fosents.kotlinvendingmachine.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingsRepository : SettingsRepository {
    var generated = false
    var vendingId = ""

    override suspend fun generateVendingId() {
        generated = true
        vendingId = "generated-id"
    }

    override fun readVendingId(): Flow<String> = flow { emit(vendingId) }
}

package com.fosents.kotlinvendingmachine.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun generateVendingId()
    fun readVendingId(): Flow<String>
}

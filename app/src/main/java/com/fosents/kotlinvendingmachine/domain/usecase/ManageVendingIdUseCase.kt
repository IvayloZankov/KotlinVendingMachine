package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Use case to manage the vending ID in settings.
 * If no vending ID exists, it generates a new one.
 */
class ManageVendingIdUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke() {
        val currentId = settingsRepository.readVendingId().first()
        if (currentId.isEmpty()) {
            settingsRepository.generateVendingId()
        }
    }
}

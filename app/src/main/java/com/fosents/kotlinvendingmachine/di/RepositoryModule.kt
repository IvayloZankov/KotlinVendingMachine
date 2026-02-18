package com.fosents.kotlinvendingmachine.di

import com.fosents.kotlinvendingmachine.domain.repository.SettingsRepository
import com.fosents.kotlinvendingmachine.data.local.prefs.SettingsRepositoryImpl
import com.fosents.kotlinvendingmachine.data.repository.VendingRepositoryImpl
import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindVendingRepository(
        vehicleRepositoryImpl: VendingRepositoryImpl
    ): VendingRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}

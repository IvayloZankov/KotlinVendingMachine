package com.fosents.kotlinvendingmachine.di

import android.content.Context
import com.fosents.kotlinvendingmachine.data.local.DataStoreOperations
import com.fosents.kotlinvendingmachine.data.local.prefs.DataStoreOperationsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataRepoModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context: Context): DataStoreOperations{
        return DataStoreOperationsImpl(context = context)
    }
}

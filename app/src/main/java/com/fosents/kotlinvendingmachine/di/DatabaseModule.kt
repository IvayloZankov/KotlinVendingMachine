package com.fosents.kotlinvendingmachine.di

import android.content.Context
import androidx.room.Room
import com.fosents.kotlinvendingmachine.data.local.VendingDatabase
import com.fosents.kotlinvendingmachine.util.Constants.VENDING_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        VendingDatabase::class.java,
        VENDING_DATABASE
    ).build()
}

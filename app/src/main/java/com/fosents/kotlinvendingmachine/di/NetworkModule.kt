package com.fosents.kotlinvendingmachine.di

import com.fosents.kotlinvendingmachine.data.RemoteDataSource
import com.fosents.kotlinvendingmachine.data.RemoteDataSourceImpl
import com.fosents.kotlinvendingmachine.data.local.VendingDatabase
import com.fosents.kotlinvendingmachine.data.remote.VendingApi
import com.fosents.kotlinvendingmachine.util.RequestUrl.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideVendingApi(retrofit: Retrofit): VendingApi {
        return retrofit.create(VendingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        vendingApi: VendingApi,
        vendingDatabase: VendingDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(vendingApi, vendingDatabase)
    }
}

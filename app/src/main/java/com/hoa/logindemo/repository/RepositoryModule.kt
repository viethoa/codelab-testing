package com.hoa.logindemo.repository

import com.hoa.logindemo.repository.config.BaseUrl
import com.hoa.logindemo.repository.service.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class RepositoryModule {

    open fun getUrlBase(): Any = "https://driver-app-backend-staging.rida.ai"

    @Provides
    @BaseUrl
    fun provideBaseUrl(): Any = getUrlBase()

    @Provides
    @Singleton
    fun provideUserApiService(@BaseUrl baseUrl: Any): UserApiService {
        val stringUrl = baseUrl as? String
        val httpUrl = baseUrl as? HttpUrl
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
        when {
            stringUrl != null -> {
                return Retrofit.Builder()
                    .baseUrl(stringUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(UserApiService::class.java)
            }
            else -> {
                return Retrofit.Builder()
                    .baseUrl(httpUrl!!)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(UserApiService::class.java)
            }
        }
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userApiService: UserApiService
    ): UserRepository {
        return UserRepositoryImpl(userApiService)
    }
}
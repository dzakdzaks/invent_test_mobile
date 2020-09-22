package com.dzakdzaks.inventmvvm.di

import com.dzakdzaks.inventmvvm.data.remote.ApiService
import com.dzakdzaks.inventmvvm.data.remote.RemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 11:14 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.di
 * ==================================//==================================
 * ==================================//==================================
 */

@Module
@InstallIn(ApplicationComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://invent-integrasi.com/")
        .client(ApiService.httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService) =
        RemoteDataSource(apiService)

}
package com.dzakdzaks.inventmvvm.di

import com.dzakdzaks.inventmvvm.data.local.ProductDao
import com.dzakdzaks.inventmvvm.data.remote.RemoteDataSource
import com.dzakdzaks.inventmvvm.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 11:17 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.di
 * ==================================//==================================
 * ==================================//==================================
 */

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: ProductDao
    ) =
        ProductRepository(remoteDataSource, localDataSource)

}
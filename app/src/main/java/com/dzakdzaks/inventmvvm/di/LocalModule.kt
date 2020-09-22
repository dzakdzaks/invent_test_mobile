package com.dzakdzaks.inventmvvm.di

import android.content.Context
import com.dzakdzaks.inventmvvm.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 11:16 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.di
 * ==================================//==================================
 * ==================================//==================================
 */
@Module
@InstallIn(ApplicationComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideProductDao(db: AppDatabase) = db.productDao()


}
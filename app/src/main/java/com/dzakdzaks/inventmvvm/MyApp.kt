package com.dzakdzaks.inventmvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 10:45 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm
 * ==================================//==================================
 * ==================================//==================================
 */
@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

}
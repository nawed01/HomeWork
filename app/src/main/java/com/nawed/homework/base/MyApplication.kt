package com.nawed.homework.base

import android.app.Application
import android.content.Context
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    companion object {
        var appContext: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext!!
    }
}
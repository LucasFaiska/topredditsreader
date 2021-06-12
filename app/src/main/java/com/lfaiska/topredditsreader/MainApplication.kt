package com.lfaiska.topredditsreader

import android.app.Application
import com.lfaiska.topredditsreader.data.di.dataModule
import com.lfaiska.topredditsreader.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(appModule, dataModule))
        }
    }
}
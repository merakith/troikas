package org.troikas.main

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.troikas.main.di.appModule

class TroikasApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TroikasApp)
            modules(appModule)
        }
    }
}
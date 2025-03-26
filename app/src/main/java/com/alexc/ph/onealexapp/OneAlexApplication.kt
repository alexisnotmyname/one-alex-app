package com.alexc.ph.onealexapp

import android.app.Application
import com.alexc.ph.data.di.dataModule
import com.alexc.ph.onealexapp.di.appModule
import com.alexc.ph.onealexapp.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OneAlexApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@OneAlexApplication)
            modules(
                appModule,
                dataModule,
                useCaseModule
            )
        }
    }
}
package com.rocketseat.rocketia

import android.app.Application
import com.rocketseat.rocketia.di.dataModule
import com.rocketseat.rocketia.di.domainModule
import com.rocketseat.rocketia.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RocketAIApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RocketAIApplication)
            modules(
                dataModule,
                domainModule,
                uiModule
            )
        }
    }

}
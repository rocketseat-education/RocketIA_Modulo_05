package com.rocketseat.rocketia

import android.app.Application
import com.rocketseat.rocketia.core.di.dataModule
import com.rocketseat.rocketia.core.di.domainModule
import com.rocketseat.rocketia.core.di.uiModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@HiltAndroidApp
class RocketAIApplication: Application() {

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidLogger()
//            androidContext(this@RocketAIApplication)
//            modules(
//                dataModule,
//                domainModule,
//                uiModule
//            )
//        }
    }
}
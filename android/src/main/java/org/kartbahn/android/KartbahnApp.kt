package org.kartbahn.android

import android.app.Application
import org.kartbahn.android.di.appModule
import org.kartbahn.di.initKoin
import org.koin.core.component.KoinComponent

class KartbahnApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            LogbackSdCardLoggerConfigurator().startLogging(this)
        }
        initKoin(appModule)
    }
}
package de.cgi
import android.app.Application
import de.cgi.android.di.appModule
import de.cgi.common.api.setBaseUrl
import de.cgi.common.di.commonModule
import de.cgi.common.di.initKoin
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.KoinAppDeclaration

class TimetrackingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Koin
        initKoin(enableNetworkLogs = true, appDeclaration = appDeclaration())

    }

    private fun appDeclaration(): KoinAppDeclaration = {
        androidContext(this@TimetrackingApplication)
        modules(commonModule(false), appModule)
    }
}

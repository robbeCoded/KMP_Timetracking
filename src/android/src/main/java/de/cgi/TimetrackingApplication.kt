package de.cgi
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import de.cgi.android.di.appModule

import de.cgi.common.dashboard.commonModule
import de.cgi.common.dashboard.initKoin
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.KoinAppDeclaration

@RequiresApi(Build.VERSION_CODES.M)
class TimetrackingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Koin
        initKoin(enableNetworkLogs = true, appDeclaration = appDeclaration())
        instance = this
    }
    private fun appDeclaration(): KoinAppDeclaration = {
        androidContext(this@TimetrackingApplication)
        modules(commonModule(false), appModule)
    }

    companion object {
        var instance: TimetrackingApplication? = null
    }
}

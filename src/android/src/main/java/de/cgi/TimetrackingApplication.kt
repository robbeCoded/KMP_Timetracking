package de.cgi

import android.app.Application
import de.cgi.android.di.appModule
import de.cgi.common.di.commonModule
import org.kodein.di.DI
import org.kodein.di.DIAware

class TimetrackingApplication : Application(), DIAware {

    override val di: DI by DI.lazy {
        import(commonModule)
        import(appModule(this@TimetrackingApplication))
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        var instance: TimetrackingApplication? = null
    }
}

package de.cgi.common.di

import de.cgi.common.auth.SignUpViewModel
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.di.commonModule
import kotlinx.browser.localStorage
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import org.w3c.dom.Storage

val webModule = DI.Module("webModule") {
    import(commonModule)
    bind<KeyValueStorage>() with singleton { KeyValueStorage(provideLocalStorage()) }
    bind<SignUpViewModel>() with singleton { SignUpViewModel(instance(), instance()) }
}

fun provideLocalStorage(): Storage {
    return localStorage
}


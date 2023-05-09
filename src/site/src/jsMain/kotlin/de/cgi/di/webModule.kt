package de.cgi.di

import de.cgi.common.auth.SignUpViewModel
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.di.commonModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val webModule = DI.Module("webModule") {
    import(commonModule)
    bind<KeyValueStorage>() with singleton { KeyValueStorage() }
    bind<SignUpViewModel>() with singleton { SignUpViewModel(instance(), instance()) }
}
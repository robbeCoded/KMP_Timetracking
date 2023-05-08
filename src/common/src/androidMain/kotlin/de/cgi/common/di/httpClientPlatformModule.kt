package de.cgi.common.di

import io.ktor.client.engine.*
import io.ktor.client.engine.android.Android
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

actual fun httpClientPlatformModule() = DI.Module("httpClientPlatformModule") {
    bind<HttpClientEngine>() with singleton { Android.create() }
}



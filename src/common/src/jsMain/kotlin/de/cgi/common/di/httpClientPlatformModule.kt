package de.cgi.common.di

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

actual fun httpClientPlatformModule() = DI.Module("httpClientPlatformModule") {
    bind<HttpClientEngine>() with singleton { Js.create() }
}
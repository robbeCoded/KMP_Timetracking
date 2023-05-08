package de.cgi.common

import io.ktor.client.engine.js.*
import org.koin.dsl.module

actual fun httpClientPlatformModule() = module {
    single { Js.create() }
}
package de.cgi.common

import io.ktor.client.engine.android.*
import org.koin.dsl.module

actual fun httpClientPlatformModule() = module {
    single { Android.create() }
}


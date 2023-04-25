package de.cgi.common

import android.content.Context
import io.ktor.client.engine.android.*
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Android.create() }
}


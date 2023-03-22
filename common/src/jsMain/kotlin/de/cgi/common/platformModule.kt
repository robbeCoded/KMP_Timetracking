package de.cgi.common

import io.ktor.client.engine.js.*
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Js.create() }
}
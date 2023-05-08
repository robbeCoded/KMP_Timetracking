package de.cgi.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun main() {
    val koinModules = listOf<Module>(
        // Add your Koin modules here
        module {

        }
    )

    startKoin {
        modules(koinModules)
    }

    // Your web app initialization code
}

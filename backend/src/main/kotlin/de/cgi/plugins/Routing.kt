package de.cgi.plugins

import de.cgi.*
import de.cgi.data.datasource.TimeEntryDataSource
import de.cgi.data.datasource.UserDataSource
import de.cgi.security.hashing.HashingService
import de.cgi.security.token.TokenConfig
import de.cgi.security.token.TokenService
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    timeEntryDataSource: TimeEntryDataSource
) {
    routing {
        signIn(userDataSource, hashingService, tokenService, tokenConfig)
        signUp(hashingService,userDataSource)
        newTimeEntry(timeEntryDataSource)
        authenticate()
        getSecretInfo()

    }
}

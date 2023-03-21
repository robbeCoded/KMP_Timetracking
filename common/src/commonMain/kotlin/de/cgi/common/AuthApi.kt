package de.cgi.common

import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import io.ktor.client.statement.*

// commonMain
interface AuthApi {
    suspend fun signUp(request: SignUpRequest): HttpResponse
    suspend fun signIn(request: AuthRequest): HttpResponse
    suspend fun authenticate(token: String): HttpResponse
}

package de.cgi.common.api

import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.GetUserIdResponse
import io.ktor.client.statement.*

// commonMain
interface AuthApi {
    suspend fun signUp(request: SignUpRequest)
    suspend fun signIn(request: AuthRequest): AuthResponse
    suspend fun authenticate(token: String): HttpResponse

    suspend fun getUserId(token: String): GetUserIdResponse
}

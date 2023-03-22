package de.cgi.common.api

import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AuthApiImpl(private val client: HttpClient) : AuthApi {
    override suspend fun signUp(request: SignUpRequest): HttpResponse {
        return client.post(routes.SIGNUP) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun signIn(request: AuthRequest): HttpResponse {
        return client.post(routes.SIGNIN) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun authenticate(token: String): HttpResponse {
        return client.get(routes.AUTHENTICATE) {
            header("Authorization", "Bearer $token")
        }
    }
}

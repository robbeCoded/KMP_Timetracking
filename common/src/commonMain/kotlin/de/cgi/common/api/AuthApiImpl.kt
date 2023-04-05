package de.cgi.common.api

import de.cgi.common.ErrorEntity
import de.cgi.common.ResultState
import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.GetUserIdResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AuthApiImpl(private val client: HttpClient) : AuthApi {
    override fun signUp(request: SignUpRequest): Flow<ResultState<AuthResult<String>>> {
        return callbackFlow {
            val response = client.post(routes.SIGNUP) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(AuthResult.SignUpSuccess()))
                HttpStatusCode.Conflict -> trySend(
                    ResultState.Success(
                        AuthResult.SignUpFailure(data = response.bodyAsText())
                    )
                )
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }

    override fun signIn(request: AuthRequest): Flow<ResultState<AuthResult<AuthResponse>>> {
        return callbackFlow {
            val response = client.post(routes.SIGNIN) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(AuthResult.Authorized(response.body())))
                HttpStatusCode.Conflict -> trySend(
                    ResultState.Success(
                            AuthResult.Unauthorized()
                    )
                )
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }

    override fun authenticate(): Flow<ResultState<AuthResult<Unit>>> {
        return callbackFlow {
            val response = client.get(routes.AUTHENTICATE) {
                contentType(ContentType.Application.Json)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(AuthResult.Authorized()))
                HttpStatusCode.Conflict -> trySend(
                    ResultState.Success(
                        AuthResult.Unauthorized()
                    )
                )
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }

    override fun getUserId(): Flow<ResultState<AuthResult<GetUserIdResponse>>> {
        return callbackFlow {
            val response = client.get(routes.GET_USER_ID) {
                contentType(ContentType.Application.Json)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(AuthResult.Authorized(response.body())))
                HttpStatusCode.Conflict -> trySend(
                    ResultState.Success(
                        AuthResult.Unauthorized()
                    )
                )
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }
}

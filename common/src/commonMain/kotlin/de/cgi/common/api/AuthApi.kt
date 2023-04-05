package de.cgi.common.api

import de.cgi.common.ResultState
import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.GetUserIdResponse
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow

// commonMain
interface AuthApi {
    fun signUp(request: SignUpRequest): Flow<ResultState<AuthResult<String>>>
    fun signIn(request: AuthRequest): Flow<ResultState<AuthResult<AuthResponse>>>
    fun authenticate(): Flow<ResultState<AuthResult<Unit>>>
    fun getUserId(): Flow<ResultState<AuthResult<GetUserIdResponse>>>
}

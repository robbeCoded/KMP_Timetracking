package de.cgi.common.api

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.GetUserRole
import kotlinx.coroutines.flow.Flow

// commonMain
interface AuthApi {
    fun signUp(request: SignUpRequest): Flow<ResultState<AuthResult<AuthResponse>>>
    fun signIn(request: AuthRequest): Flow<ResultState<AuthResult<AuthResponse>>>
    fun authenticate(): Flow<ResultState<AuthResult<Unit>>>
    fun getRole(): Flow<ResultState<AuthResult<GetUserRole>>>
}

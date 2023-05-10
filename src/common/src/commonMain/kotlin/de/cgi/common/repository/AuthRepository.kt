package de.cgi.common.repository

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.GetUserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(name: String, email: String, password: String): Flow<ResultState<AuthResult<AuthResponse>>>
    fun signIn(email: String, password: String): Flow<ResultState<AuthResult<AuthResponse>>>
    fun authenticate(): Flow<ResultState<AuthResult<Unit>>>
    fun getUserRole(): Flow<ResultState<AuthResult<GetUserRole>>>
}
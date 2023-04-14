package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.GetUserIdResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(name: String, email: String, password: String): Flow<ResultState<AuthResult<AuthResponse>>>
    fun signIn(email: String, password: String): Flow<ResultState<AuthResult<AuthResponse>>>
    fun authenticate(): Flow<ResultState<AuthResult<Unit>>>
    fun getUserId(): Flow<ResultState<AuthResult<GetUserIdResponse>>>
}
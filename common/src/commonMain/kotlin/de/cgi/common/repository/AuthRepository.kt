package de.cgi.common.repository

import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult

interface AuthRepository {
    suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit>
    suspend fun signIn(email: String, password: String): AuthResult<AuthResponse>
    suspend fun authenticate(): AuthResult<Unit>
}
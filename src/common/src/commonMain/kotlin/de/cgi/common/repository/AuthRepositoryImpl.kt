package de.cgi.common.repository

import de.cgi.common.util.ResultState
import de.cgi.common.api.AuthApi
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.GetUserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: KeyValueStorage,
) : AuthRepository {
    override fun signUp(
        name: String,
        email: String,
        password: String
    ): Flow<ResultState<AuthResult<AuthResponse>>> {
        val signUpRequest = SignUpRequest(email, password, name)
        return api.signUp(signUpRequest)
    }

    override fun signIn(
        email: String,
        password: String
    ): Flow<ResultState<AuthResult<AuthResponse>>> {
        val authRequest = AuthRequest(email, password)
        return api.signIn(authRequest).map { result ->
            if (result is ResultState.Success && result.data.data?.token != null && result.data.data.userId != null) {
                result.data.data.let { it.token?.let { it1 -> prefs.putString("jwt", it1) } }
                result.data.data.let { it.userId?.let { it1 -> prefs.putString("userId", it1) } }
                result.data.data.let { it.username?.let { it1 -> prefs.putString("username", it1) } }
            }
            result
        }
    }

    override fun authenticate(): Flow<ResultState<AuthResult<Unit>>> {
        return api.authenticate()
    }

    override fun getUserRole(): Flow<ResultState<AuthResult<GetUserRole>>> {
        return api.getRole().map { result ->
            if (result is ResultState.Success) {
                result.data.data?.let { prefs.putString("role", it.role) }
            }
            result
        }
    }

}
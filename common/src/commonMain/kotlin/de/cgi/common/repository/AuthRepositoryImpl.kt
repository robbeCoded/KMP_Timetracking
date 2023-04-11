package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.api.AuthApi
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.GetUserIdResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: KeyValueStorage,
) : AuthRepository {
    override fun signUp(
        name: String,
        email: String,
        password: String
    ): Flow<ResultState<AuthResult<String>>> {
        val signUpRequest = SignUpRequest(email, password, name)
        return api.signUp(signUpRequest)
    }

    override fun signIn(
        email: String,
        password: String
    ): Flow<ResultState<AuthResult<AuthResponse>>> {
        val authRequest = AuthRequest(email, password)
        return api.signIn(authRequest).map { result ->
            if (result is ResultState.Success) {
                result.data.data?.let { prefs.putString("jwt", it.token) }
                result.data.data?.let { prefs.putString("userId", it.userId) }
            }
            result
        }
    }

    override fun authenticate(): Flow<ResultState<AuthResult<Unit>>> {
        return api.authenticate()
    }

    override fun getUserId(): Flow<ResultState<AuthResult<GetUserIdResponse>>> {
        return api.getUserId().map { result ->
            if (result is ResultState.Success) {
                result.data.data?.let { prefs.putString("userId", it.userId) }
            }
            result
        }
    }

}
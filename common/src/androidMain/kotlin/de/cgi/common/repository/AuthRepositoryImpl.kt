package android.repository

import android.AndroidHttpClient
import android.content.SharedPreferences
import de.cgi.common.AuthApi
import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResponse
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.repository.AuthRepository
import de.cgi.common.routes
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) : AuthRepository {
    // Implement the AuthRepository methods here

    override suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit> {
        val signUpRequest = SignUpRequest(email, password, name)
        return try {
            api.signUp(signUpRequest)
            signIn(email, password)
            AuthResult.Authorized()
        } catch (e: Exception) {
            // Handle different error cases
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult<AuthResponse> {
        val authRequest = AuthRequest(email, password)
        return try {
            val response = api.signIn(authRequest)
            val token = response.body<String>()
            prefs.edit()
                .putString("jwt", token)
                .apply()
            AuthResult.Authorized()
        } catch (e: Exception) {
            // Handle different error cases
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate(token)
            AuthResult.Authorized()
        } catch (e: Exception) {
            // Handle different error cases
            AuthResult.UnknownError()
        }
    }
}
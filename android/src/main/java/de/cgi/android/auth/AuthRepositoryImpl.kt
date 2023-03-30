package de.cgi.android.auth


import android.content.SharedPreferences
import coil.network.HttpException
import de.cgi.common.api.AuthApi
import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.repository.AuthRepository
import io.ktor.client.call.*


class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) : AuthRepository {
    override suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit> {
        val signUpRequest = SignUpRequest(email, password, name)
        return try {
            api.signUp(signUpRequest)
            signIn(email, password)
        } catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult<Unit> {
        val authRequest = AuthRequest(email, password)
        return try {
            val response = api.signIn(authRequest)
            prefs.edit()
                .putString("jwt", response.token)
                .apply()
            prefs.edit()
                .putString("userId", response.userId)
                .apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate(token)
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                println(e.message)
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            println(e.message)
            AuthResult.UnknownError()
        }
    }

    override suspend fun getUserId(): AuthResult<String> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            val response = api.getUserId(token)
            prefs.edit()
                .putString("userId", response.userId)
                .apply()
            AuthResult.Authorized(response.userId)
        }
        catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                println(e.message)
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            println(e.message)
            AuthResult.UnknownError()
        }
    }

}
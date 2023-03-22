package de.cgi.android.model


import coil.network.HttpException
import de.cgi.common.api.AuthApi
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.data.model.requests.AuthRequest
import de.cgi.common.data.model.requests.SignUpRequest
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.repository.AuthRepository
import io.ktor.client.call.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AuthRepositoryImpl : AuthRepository, KoinComponent {
    private val api by inject<AuthApi>()
    private val prefs by inject<KeyValueStorage>()
    //private val prefs by inject<AndroidSharedPreferencesStorage>()
    override suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit> {
        val signUpRequest = SignUpRequest(email, password, name)
        println("$name $email $password")
        return try {
            api.signUp(signUpRequest)
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult<Unit> {
        val authRequest = AuthRequest(email, password)
        return try {
            val response = api.signIn(authRequest)
            val token = response.body<String>()
            prefs.putString("jwt", token)
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}
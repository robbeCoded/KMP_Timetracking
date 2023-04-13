package de.cgi.android.auth

import de.cgi.common.ResultState
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.GetUserIdResponse
import de.cgi.common.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthUseCase(
    private val authRepository: AuthRepository
) {

    fun signUp(name: String, email: String, password: String): Flow<SignUpState> {
        return authRepository.signUp(name, email, password).map { resultState ->
            when(resultState) {
                is ResultState.Loading -> SignUpState.Loading
                is ResultState.Success -> {
                    when(val authResult = resultState.data) {
                        is AuthResult.AuthFailure -> SignUpState.Failure(authResult.data?.message)
                        is AuthResult.SignUpSuccess -> SignUpState.Success
                        else -> {SignUpState.Error(authResult.data?.message)}
                    }
                }
                is ResultState.Error -> SignUpState.Error(resultState.entity.message)
                else -> {SignUpState.Error("Unknown error occurred")}
            }
        }
    }

    fun signIn(email: String, password: String): Flow<SignInState> {
        return authRepository.signIn(email, password).map {resultState ->
            when(resultState) {
                is ResultState.Loading -> SignInState.Loading
                is ResultState.Success -> {
                    when(val authResult = resultState.data) {
                        is AuthResult.Authorized -> SignInState.Success(authResult.data)
                        is AuthResult.Unauthorized -> SignInState.Unauthorized
                        is AuthResult.AuthFailure -> SignInState.Failure(authResult.data?.message.toString())
                        else -> SignInState.Error(authResult.data?.message)
                    }
                }
                is ResultState.Error -> SignInState.Error(resultState.entity.message ?: "Unknown error occurred")
                else -> SignInState.Error("Unknown error occurred")
            }
        }
    }

    fun authenticate(): Flow<SignInState> {
        return authRepository.authenticate().map {resultState ->
            when(resultState) {
                is ResultState.Loading -> SignInState.Loading
                is ResultState.Success -> {
                    when(resultState.data) {
                        is AuthResult.Authorized -> SignInState.Authorized
                        is AuthResult.Unauthorized -> SignInState.Unauthorized
                        else -> SignInState.Error("Unknown error occurred")
                    }
                }
                is ResultState.Error -> SignInState.Error(resultState.entity.message)
                else -> SignInState.Error("Unknown error occurred")
            }
        }
    }
    fun getUserId(): Flow<ResultState<AuthResult<GetUserIdResponse>>> {
        return authRepository.getUserId()
    }

}
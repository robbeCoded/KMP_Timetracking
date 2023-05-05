package de.cgi.common.auth

import de.cgi.common.ResultState
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    fun signUp(name: String, email: String, password: String): Flow<SignUpState> {
        return authRepository.signUp(name, email, password).map { resultState ->
            when (resultState) {
                is ResultState.Loading -> SignUpState.Loading
                is ResultState.Success -> {
                    when (val authResult = resultState.data) {
                        is AuthResult.AuthFailure -> SignUpState.Failure(authResult.data?.message.toString())
                        is AuthResult.SignUpSuccess -> SignUpState.Success
                        else -> {
                            SignUpState.Error(authResult.data?.message)
                        }
                    }
                }
                is ResultState.Error -> SignUpState.Error(resultState.entity.message)
                else -> {
                    SignUpState.Error("Unknown error occurred")
                }
            }
        }
    }
}
package de.cgi.common.auth


import de.cgi.common.UserRepository
import kotlinx.coroutines.flow.StateFlow

expect class SignInViewModel(
    useCase: SignInUseCase,
    userRepository: UserRepository
) {
    val userId: String
    val signInState: StateFlow<SignInState?>

    fun signIn()
    fun signInEmailChanged(email: String)
    fun signInPasswordChanged(password: String)
    fun getUserRole()
}

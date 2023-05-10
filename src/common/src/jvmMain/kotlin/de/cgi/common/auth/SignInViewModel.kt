package de.cgi.common.auth

import de.cgi.common.UserRepository
import kotlinx.coroutines.flow.StateFlow

actual class SignInViewModel actual constructor(
    useCase: SignInUseCase,
    userRepository: UserRepository
) {
    actual val userId: String
        get() = TODO("Not yet implemented")
    actual val signInState: StateFlow<SignInState?>
        get() = TODO("Not yet implemented")

    actual fun signIn() {
    }

    actual fun signInEmailChanged(email: String) {
    }

    actual fun signInPasswordChanged(password: String) {
    }

    actual fun getUserRole() {
    }

}
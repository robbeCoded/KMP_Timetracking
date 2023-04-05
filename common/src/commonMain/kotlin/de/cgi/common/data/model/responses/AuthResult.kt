package de.cgi.common.data.model.responses

sealed class AuthResult<T>(val data: T? = null) {
    class Authorized<T>(data: T? = null): AuthResult<T>(data)
    class SignUpSuccess<T>: AuthResult<T>()
    class SignUpFailure<T>(data: T? = null): AuthResult<T>(data)
    class Unauthorized<T>: AuthResult<T>()
    class UnknownError<T>: AuthResult<T>()
}

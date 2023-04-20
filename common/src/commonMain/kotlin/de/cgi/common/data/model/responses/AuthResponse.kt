package de.cgi.common.data.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val message: String?,
    val token: String?,
    val userId: String?,
    val username: String?
)


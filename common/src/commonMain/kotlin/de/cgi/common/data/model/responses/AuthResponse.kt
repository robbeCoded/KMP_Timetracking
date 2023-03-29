package de.cgi.common.data.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val userId: String,
)


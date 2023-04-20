package de.cgi.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class GetUserRoleResponse(
    val role: String,
)

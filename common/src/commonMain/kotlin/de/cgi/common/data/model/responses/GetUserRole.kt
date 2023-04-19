package de.cgi.common.data.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class GetUserRole(
    val role: String
)

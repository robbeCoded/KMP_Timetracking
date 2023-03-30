package de.cgi.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class GetUserIdResponse(
    val userId: String,
)

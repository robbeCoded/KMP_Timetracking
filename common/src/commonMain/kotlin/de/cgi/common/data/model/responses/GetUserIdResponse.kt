package de.cgi.common.data.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class GetUserIdResponse(
    val userId: String
)

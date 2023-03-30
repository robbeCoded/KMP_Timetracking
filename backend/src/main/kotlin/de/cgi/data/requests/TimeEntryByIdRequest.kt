package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class TimeEntryByIdRequest(
    val id: String
)

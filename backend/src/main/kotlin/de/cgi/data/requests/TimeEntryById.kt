package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class TimeEntryById(
    val id: String
)

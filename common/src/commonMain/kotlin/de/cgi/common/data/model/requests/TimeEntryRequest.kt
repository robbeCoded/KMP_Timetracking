package de.cgi.common.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class TimeEntryRequest (
    val token: String,
    val id: String
)
package de.cgi.common.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class TimeEntryRequest (
    val id: String
)
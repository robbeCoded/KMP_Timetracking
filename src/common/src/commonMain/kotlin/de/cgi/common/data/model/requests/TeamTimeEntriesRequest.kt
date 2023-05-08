package de.cgi.common.data.model.requests

@kotlinx.serialization.Serializable
data class TeamTimeEntriesRequest(
    val userIds: List<String>,
    val date: String
)

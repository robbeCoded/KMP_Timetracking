package de.cgi.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val name: String,
    val id: String,
    val managerId: String,
    val teamMemberIds: List<String>?
)

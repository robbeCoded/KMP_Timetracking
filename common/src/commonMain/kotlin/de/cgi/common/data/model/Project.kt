package de.cgi.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val id: String,
    val name: String,
    val description: String?,
    val startDate: String,
    val endDate: String,
    val userId: String,
    val color: String?
)

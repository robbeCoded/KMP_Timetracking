package de.cgi.data.requests

import de.cgi.data.models.LocalDateTimeSerializer
import de.cgi.data.models.ObjectIdSerializer
import kotlinx.serialization.Serializable

@Serializable
data class NewTimeEntry(
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: String,
    @Serializable(with = ObjectIdSerializer::class)
    val userId: String,
    val description: String?,
    @Serializable(with = ObjectIdSerializer::class)
    val projectId: String?
)

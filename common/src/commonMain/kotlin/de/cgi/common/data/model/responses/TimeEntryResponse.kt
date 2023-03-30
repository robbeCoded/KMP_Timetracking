package de.cgi.common.data.model.responses

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable


@Serializable
data class TimeEntryResponse(
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val timestamp: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val endTime: LocalDateTime,
    val id: String,
    val projectId: String?,
    val description: String?,
    val userId: String
)
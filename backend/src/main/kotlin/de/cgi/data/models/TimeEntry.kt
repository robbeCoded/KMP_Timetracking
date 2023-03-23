package de.cgi.data.models

import java.time.LocalDateTime
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


@Serializable
data class TimeEntry(
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: LocalDateTime,
    @Serializable(with = ObjectIdSerializer::class)
    @BsonId val id: ObjectId = ObjectId(),
    val project: String?,
    val description: String?,
    val userId: String
)

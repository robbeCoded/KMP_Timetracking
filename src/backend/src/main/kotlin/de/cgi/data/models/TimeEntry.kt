package de.cgi.data.models

import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class TimeEntry(
    @Serializable(with = ObjectIdSerializer::class)
    @BsonId val id: ObjectId = ObjectId(),

    val timestamp: String,
    val date: String,
    val startTime: String,
    val endTime: String,

    @Serializable(with = ObjectIdSerializer::class)
    val projectId: ObjectId?,
    val description: String?,

    @Serializable(with = ObjectIdSerializer::class)
    val userId: ObjectId,
)

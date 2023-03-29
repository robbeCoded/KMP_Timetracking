package de.cgi.data.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
@Serializable
data class Project(

    val name: String,

    val description: String?,

    @Serializable(with = ObjectIdSerializer::class)
    @BsonId val id: ObjectId = ObjectId(),

    @Serializable(with = LocalDateIso8601Serializer::class)
    val startDate: LocalDate,

    @Serializable(with = LocalDateIso8601Serializer::class)
    val endDate: LocalDate,

    @Serializable(with = ObjectIdSerializer::class)
    val userId: ObjectId,
)

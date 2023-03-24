package de.cgi.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDate
@Serializable
data class Project(

    val name: String,

    val description: String?,

    @Serializable(with = ObjectIdSerializer::class)
    @BsonId val id: ObjectId = ObjectId(),

    @Serializable(with = LocalDateSerializer::class)
    val startDate: LocalDate,

    @Serializable(with = LocalDateSerializer::class)
    val endDate: LocalDate,

    @Serializable(with = ObjectIdSerializer::class)
    val userId: ObjectId,
)

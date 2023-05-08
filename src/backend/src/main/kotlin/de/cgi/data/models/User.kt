package de.cgi.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
@Serializable
data class User(
    val name: String,
    val email: String,
    val hashedPassword: String,
    val salt: String,

    @Serializable(with = ObjectIdSerializer::class)
    @BsonId val id: ObjectId = ObjectId(),

    val role: String?,

    @Serializable(with = ObjectIdSerializer::class)
    val teamId: ObjectId?
)
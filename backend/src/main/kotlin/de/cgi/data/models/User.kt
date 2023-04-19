package de.cgi.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    val name: String,
    val email: String,
    val hashedPassword: String,
    val salt: String,
    @BsonId val id: ObjectId = ObjectId(),
    val role: String?,
    val teamId: ObjectId?
)
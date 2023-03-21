package de.cgi.data.datasource

import de.cgi.data.models.User

interface UserDataSource {
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User): Boolean
}
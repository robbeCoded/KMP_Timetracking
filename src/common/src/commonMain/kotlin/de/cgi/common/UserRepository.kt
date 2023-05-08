package de.cgi.common

expect class UserRepository {
    fun getUserId(): String
    fun getUserName(): String
    fun getUserRole(): String
}
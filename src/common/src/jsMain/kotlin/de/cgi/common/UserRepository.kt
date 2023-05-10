package de.cgi.common

import org.w3c.dom.Storage
import org.w3c.dom.get

actual class UserRepository(private val keyValueStorage: Storage) {

    actual fun getUserId(): String {
        return keyValueStorage["userId"] ?: ""
    }

    actual fun getUserName(): String {
        return keyValueStorage["username"] ?: ""
    }

    actual fun getUserRole(): String {
        return keyValueStorage["role"] ?: ""
    }
}
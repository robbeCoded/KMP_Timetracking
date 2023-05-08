package de.cgi.common

import de.cgi.common.data.model.KeyValueStorage

actual class UserRepository(private val keyValueStorage: KeyValueStorage) {
    actual fun getUserId(): String {
        return keyValueStorage.getString("userId", "") ?: ""
    }

    actual fun getUserName(): String {
        return keyValueStorage.getString("username", "") ?: ""
    }

    actual fun getUserRole(): String {
        return keyValueStorage.getString("role", "") ?: ""
    }
}
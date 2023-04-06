package de.cgi.common

import de.cgi.common.data.model.KeyValueStorage

actual class UserRepository(private val keyValueStorage: KeyValueStorage) {
    actual fun getUserId(): String {
        return keyValueStorage.getString("userId", "") ?: ""
    }
}
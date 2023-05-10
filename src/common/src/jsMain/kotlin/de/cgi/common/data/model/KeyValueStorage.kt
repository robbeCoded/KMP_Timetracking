package de.cgi.common.data.model

import kotlinx.browser.localStorage
import org.w3c.dom.Storage
import org.w3c.dom.get
import org.w3c.dom.set

actual class KeyValueStorage(private val keyValueStorage: Storage) {

    actual fun getString(key: String, defaultValue: String?): String? {
        return keyValueStorage[key] ?: defaultValue
    }

    actual fun putString(key: String, value: String) {
        keyValueStorage[key] = value
    }

    fun remove(key: String) {
        keyValueStorage.removeItem(key)
    }
}
package de.cgi.common.data.model

import kotlinx.browser.localStorage
import org.w3c.dom.get
import org.w3c.dom.set

actual class KeyValueStorage {
    private val storage = localStorage

    actual fun getString(key: String, defaultValue: String?): String? {
        return storage[key] ?: defaultValue
    }

    actual fun putString(key: String, value: String) {
        storage[key] = value
    }

    fun remove(key: String) {
        storage.removeItem(key)
    }
}
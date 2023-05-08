package de.cgi.common.data.model

import android.content.SharedPreferences

actual class KeyValueStorage (private val prefs: SharedPreferences) {

    actual fun getString(key: String, defaultValue: String?): String? {
        return prefs.getString(key, defaultValue)
    }

    actual fun putString(key: String, value: String) {
        prefs.edit()
            .putString(key, value)
            .apply()
    }
}
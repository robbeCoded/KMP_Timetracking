package de.cgi.android.model

import android.content.SharedPreferences
import de.cgi.common.data.model.KeyValueStorage

class AndroidSharedPreferencesStorage(private val prefs: SharedPreferences) : KeyValueStorage {
    override fun getString(key: String, defaultValue: String?): String? {
        return prefs.getString(key, defaultValue)
    }

    override fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}

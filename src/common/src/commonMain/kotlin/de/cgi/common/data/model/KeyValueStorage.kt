package de.cgi.common.data.model

expect class KeyValueStorage {
    fun getString(key: String, defaultValue: String?): String?
    fun putString(key: String, value: String)
}

package de.cgi.common.util

data class ErrorEntity(
    val throwable: Throwable? = null,
    val id: String? = null,
    val message: String? = null,
)
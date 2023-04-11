package de.cgi.common

data class ErrorEntity(
    val throwable: Throwable? = null,
    val id: String? = null,
    val message: String? = null,
)
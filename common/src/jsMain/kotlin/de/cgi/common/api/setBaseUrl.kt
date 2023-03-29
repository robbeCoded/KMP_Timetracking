package de.cgi.common.api

import de.cgi.common.api.routes.BASE_URL

actual fun setBaseUrl(url: String) {
    BASE_URL = url
}
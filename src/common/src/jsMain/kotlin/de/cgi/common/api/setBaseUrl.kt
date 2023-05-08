package de.cgi.common.api

import de.cgi.common.api.Routes.BASE_URL

actual fun setBaseUrl(url: String) {
    BASE_URL = url
}
package de.cgi

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import de.cgi.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
        }

    }
}

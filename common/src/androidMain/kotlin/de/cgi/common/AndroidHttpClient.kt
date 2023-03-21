package android

import de.cgi.common.PlatformHttpClient
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*


// androidMain
class AndroidHttpClient : PlatformHttpClient {
    override fun create(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }
}

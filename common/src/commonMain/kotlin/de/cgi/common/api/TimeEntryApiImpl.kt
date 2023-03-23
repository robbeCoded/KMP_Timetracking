package de.cgi.common.api

import de.cgi.common.data.model.requests.NewTimeEntry
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class TimeEntryApiImpl(
    private val client: HttpClient
) : TimeEntryApi {
    override suspend fun newTimeEntry(timeEntry: NewTimeEntry, token: String): HttpResponse {
        return client.post(routes.NEW_TIME_ENTRY) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(timeEntry)
        }
    }

    override suspend fun getTimeEntries(token: String): HttpResponse {
        return client.get(routes.GET_TIME_ENTRIES) {
            header("Authorization", "Bearer $token")
        }
    }

    override suspend fun getTimeEntryById(id: String, token: String): HttpResponse {
        return client.get(routes.GET_TIME_ENTRY_BY_ID) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(id)
        }
    }

    override suspend fun deleteTimeEntry(id: String, token: String): HttpResponse {
        return client.delete(routes.DELETE_TIME_ENTRY) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(id)
        }
    }
}
package de.cgi.common.api

import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.requests.TimeEntryRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class TimeEntryApiImpl(private val client: HttpClient) : TimeEntryApi{

    override suspend fun newTimeEntry(timeEntry: NewTimeEntry, token: String): TimeEntry? {
        return client.post(routes.NEW_TIME_ENTRY) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(timeEntry)
        }.body()
    }

    override suspend fun getTimeEntries(token: String): List<TimeEntry> {
        return client.get(routes.GET_TIME_ENTRIES) {
            header("Authorization", "Bearer $token")
        }.body<List<TimeEntry>>().toList()
    }


    override suspend fun getTimeEntryById(timeEntryRequest: TimeEntryRequest): HttpResponse {
        return client.get(routes.GET_TIME_ENTRY_BY_ID) {
            header(HttpHeaders.Authorization, "Bearer ${timeEntryRequest.token}")
            parameter("id", timeEntryRequest.id)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun deleteTimeEntry(timeEntryRequest: TimeEntryRequest): HttpResponse {
        return client.delete(routes.DELETE_TIME_ENTRY) {
            header("Authorization", "Bearer ${timeEntryRequest.token}")
            parameter("id", timeEntryRequest.id)
            contentType(ContentType.Application.Json)
        }
    }
}
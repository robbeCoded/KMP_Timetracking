package de.cgi.common.api

import de.cgi.common.data.model.requests.NewTimeEntry
import io.ktor.client.statement.*

interface TimeEntryApi {
    suspend fun newTimeEntry(timeEntry: NewTimeEntry, token: String): HttpResponse
    suspend fun getTimeEntries(token: String): HttpResponse
    suspend fun getTimeEntryById(id: String, token: String): HttpResponse
    suspend fun deleteTimeEntry(id: String, token: String): HttpResponse
}
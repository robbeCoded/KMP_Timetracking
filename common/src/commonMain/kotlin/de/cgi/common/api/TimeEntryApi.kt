package de.cgi.common.api

import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.responses.TimeEntryResponse
import io.ktor.client.statement.*

interface TimeEntryApi {
    suspend fun newTimeEntry(timeEntry: NewTimeEntry, token: String): HttpResponse
    suspend fun getTimeEntries(token: String): List<TimeEntry>
    suspend fun getTimeEntryById(id: String, token: String): TimeEntry?
    suspend fun deleteTimeEntry(id: String, token: String): HttpResponse
}
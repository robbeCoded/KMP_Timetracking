package de.cgi.common.api

import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.requests.TimeEntryRequest
import io.ktor.client.statement.*

interface TimeEntryApi {
    suspend fun newTimeEntry(timeEntry: NewTimeEntry, token: String): TimeEntry?
    suspend fun getTimeEntries(token: String): List<TimeEntry>
    suspend fun getTimeEntryById(timeEntryRequest: TimeEntryRequest): HttpResponse
    suspend fun deleteTimeEntry(timeEntryRequest: TimeEntryRequest): HttpResponse
}
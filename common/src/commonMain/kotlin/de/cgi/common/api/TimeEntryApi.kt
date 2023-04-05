package de.cgi.common.api

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.requests.TimeEntryRequest
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow

interface TimeEntryApi {
    fun newTimeEntry(timeEntry: NewTimeEntry): Flow<ResultState<TimeEntry?>>
    fun getTimeEntries(): Flow<ResultState<List<TimeEntry>>>
    fun getTimeEntryById(timeEntryRequest: TimeEntryRequest): Flow<ResultState<TimeEntry?>>
    fun deleteTimeEntry(timeEntryRequest: TimeEntryRequest): Flow<ResultState<Boolean>>
}
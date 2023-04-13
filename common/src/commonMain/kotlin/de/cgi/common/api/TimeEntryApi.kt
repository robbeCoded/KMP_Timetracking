package de.cgi.common.api

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.requests.TimeEntryRequest
import de.cgi.common.data.model.requests.UpdateTimeEntryRequest
import kotlinx.coroutines.flow.Flow

interface TimeEntryApi {
    fun newTimeEntry(timeEntry: NewTimeEntry): Flow<ResultState<TimeEntry?>>
    fun updateTimeEntry(timeEntry: UpdateTimeEntryRequest): Flow<ResultState<TimeEntry?>>
    fun getTimeEntries(userId: String, date: String): Flow<ResultState<List<TimeEntry>>>
    fun getTimeEntryById(timeEntryRequest: TimeEntryRequest): Flow<ResultState<TimeEntry?>>
    fun deleteTimeEntry(timeEntryRequest: TimeEntryRequest): Flow<ResultState<Boolean>>
}
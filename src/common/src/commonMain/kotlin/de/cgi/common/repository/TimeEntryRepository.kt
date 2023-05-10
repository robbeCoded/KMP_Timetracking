package de.cgi.common.repository

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.TeamTimeEntriesRequest
import kotlinx.coroutines.flow.Flow

interface TimeEntryRepository {
    fun newTimeEntry(
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?
    ): Flow<ResultState<TimeEntry?>>

    fun updateTimeEntry(
        id: String,
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?
    ): Flow<ResultState<TimeEntry?>>
    fun getTimeEntriesForWeek(userId: String, startDate: String, forceReload: Boolean): Flow<ResultState<List<TimeEntry>>>
    fun getTeamTimeEntriesForWeek(request: TeamTimeEntriesRequest, forceReload: Boolean): Flow<ResultState<List<List<TimeEntry>?>>>
    fun getTimeEntryById(id: String, forceReload: Boolean): Flow<ResultState<TimeEntry?>>
    fun deleteTimeEntry(id: String): Flow<ResultState<Boolean>>
}
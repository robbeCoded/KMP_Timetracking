package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
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
    fun getTimeEntries(userId: String, forceReload: Boolean): Flow<ResultState<List<TimeEntry>>>
    fun getTimeEntryById(id: String, forceReload: Boolean): Flow<ResultState<TimeEntry?>>
    fun deleteTimeEntry(id: String): Flow<ResultState<Boolean>>
}
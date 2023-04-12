package de.cgi.android.timeentry

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.flow.Flow

class TimeEntryEditUseCase(
    private val repository: TimeEntryRepository
) {

    fun getTimeEntryById(id: String, forceReload: Boolean): Flow<ResultState<TimeEntry?>> {
        return repository.getTimeEntryById(id, forceReload)
    }

    fun deleteTimeEntry(id: String): Flow<ResultState<Boolean>> {
        return repository.deleteTimeEntry(id)
    }

    fun newTimeEntry(
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?
    ): Flow<ResultState<TimeEntry?>> {
        return repository.newTimeEntry(date, startTime, endTime, userId, description, projectId)
    }

    fun updateTimeEntry(
        id: String,
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?
    ): Flow<ResultState<TimeEntry?>> {
        return repository.updateTimeEntry(
            id,
            date,
            startTime,
            endTime,
            userId,
            description,
            projectId
        )
    }
}
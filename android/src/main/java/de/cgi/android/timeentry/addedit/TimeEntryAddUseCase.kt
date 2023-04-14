package de.cgi.android.timeentry.addedit

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.flow.Flow

class TimeEntryAddUseCase(
    private val repository: TimeEntryRepository
) {
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
}
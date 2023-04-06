package de.cgi.android.timeentry


import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.flow.Flow


class TimeEntryUseCase (
    private val repository: TimeEntryRepository
) {
    /*
        fun getTimeEntryById(id: String, forceReload: Boolean): Flow<ResultState<TimeEntry?>> {
        return repository.getTimeEntryById(id, forceReload)
    }

    fun newTimeEntry(
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?
    ): Flow<TimeEntryListState> {
        return repository.newTimeEntry(startTime, endTime, userId, description, projectId).map { resultState ->
            when(resultState) {
                is ResultState.Loading -> TimeEntryListState
            }
        }
    } */

    fun deleteTimeEntry(id: String): Flow<ResultState<Boolean>> {
        return repository.deleteTimeEntry(id)
    }
    fun getTimeEntries(forceReload: Boolean): Flow<ResultState<List<TimeEntry>>> {
        return repository.getTimeEntries(forceReload)
    }

}

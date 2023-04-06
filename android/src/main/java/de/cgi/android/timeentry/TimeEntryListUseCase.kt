package de.cgi.android.timeentry


import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.flow.Flow


class TimeEntryListUseCase (
    private val repository: TimeEntryRepository
) {
    fun deleteTimeEntry(id: String): Flow<ResultState<Boolean>> {
        return repository.deleteTimeEntry(id)
    }
    fun getTimeEntries(forceReload: Boolean): Flow<ResultState<List<TimeEntry>>> {
        return repository.getTimeEntries(forceReload)
    }

}

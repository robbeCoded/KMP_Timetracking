package de.cgi.android.timeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.data.model.TimeEntry
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class TimeEntryListViewModel(
    private val timeEntryListUseCase: TimeEntryListUseCase
) : ViewModel() {

    private var loadTimeEntriesJob: Job? = null
    private var deleteTimeEntryJob: Job? = null

    private val _listState = MutableStateFlow(TimeEntryListState())
    val listState =  _listState.asStateFlow()

    init {
        getTimeEntries()
    }

    fun getTimeEntries() {
        loadTimeEntriesJob?.cancel()
        loadTimeEntriesJob = timeEntryListUseCase.getTimeEntries(true).onEach { resultState ->
            _listState.update { it.copy(timeEntryListState = resultState) }
        }.launchIn(viewModelScope)
    }

    fun deleteTimeEntry(timeEntry: TimeEntry) {
        deleteTimeEntryJob?.cancel()
        deleteTimeEntryJob = timeEntryListUseCase.deleteTimeEntry(timeEntry.id).onEach {
            _listState.update {
                it.copy(removeTimeEntryState = null)
            }
        }.launchIn(viewModelScope)
    }

}

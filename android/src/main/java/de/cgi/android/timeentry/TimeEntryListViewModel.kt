package de.cgi.android.timeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class TimeEntryListViewModel(
    private val timeEntryListUseCase: TimeEntryListUseCase,
    private val getProjectsUseCase: TimeEntryGetProjectsUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTimeEntriesJob: Job? = null
    private var deleteTimeEntryJob: Job? = null

    private val _listState = MutableStateFlow(TimeEntryListState())
    val listState =  _listState.asStateFlow()


    private var loadProjectsJob: Job? = null
    init {
        getTimeEntries()
        getProjectMapping()
    }

    fun getTimeEntries() {
        loadTimeEntriesJob?.cancel()
        loadTimeEntriesJob = timeEntryListUseCase.getTimeEntries(userId = userId, true).onEach { resultState ->
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

    fun getProjectMapping() {
        loadProjectsJob?.cancel()
        loadProjectsJob = getProjectsUseCase.getProjects(userId = userId, forceReload = true).onEach { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    val projectMap = resultState.data.associateBy({ it.id }, { it.name })
                    _listState.update { it.copy(projectMapState = ResultState.Success(projectMap)) }
                }
                is ResultState.Error -> {
                    _listState.update { it.copy(projectMapState = resultState) }
                }
                is ResultState.Loading -> {
                    _listState.update { it.copy(projectMapState = resultState) }
                }
            }
        }.launchIn(viewModelScope)
    }





}

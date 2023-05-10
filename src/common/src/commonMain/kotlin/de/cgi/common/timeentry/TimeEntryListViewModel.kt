package de.cgi.common.timeentry


import androidx.compose.runtime.MutableState
import de.cgi.common.UserRepository
import de.cgi.common.repository.ProjectMapProvider
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

expect class TimeEntryListViewModel(
    timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository,
    projectMapProvider: ProjectMapProvider
) {
    val listState: StateFlow<TimeEntryListState>
    val updateTrigger: MutableState<Boolean>

    fun getTimeEntries()
    fun notifyTimeEntryUpdates()
    fun selectedDateChanged(date: LocalDate)
    fun getTotalDuration(): LocalTime
}

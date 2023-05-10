package de.cgi.common.timeentry

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import de.cgi.common.UserRepository
import de.cgi.common.repository.ProjectMapProvider
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

actual class TimeEntryListViewModel actual constructor(
    timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository,
    projectMapProvider: ProjectMapProvider
) {
    actual val listState: StateFlow<TimeEntryListState>
        get() = TODO("Not yet implemented")
    actual val updateTrigger: MutableState<Boolean> = mutableStateOf(false)

    actual fun getTimeEntries() {
    }

    actual fun notifyTimeEntryUpdates() {
    }

    actual fun selectedDateChanged(date: LocalDate) {
    }

    actual fun getTotalDuration(): LocalTime {
        TODO("Not yet implemented")
    }

}
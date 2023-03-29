package de.cgi.android.timetracking.newTimeEntry

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed class AddTimeEntryUiEvent {
        data class TimeEntryDayChanged(val value: LocalDate): AddTimeEntryUiEvent()
        data class TimeEntryDurationChanged(val value: LocalTime): AddTimeEntryUiEvent()
        data class TimeEntryStartChanged(val value: LocalTime): AddTimeEntryUiEvent()
        data class TimeEntryEndChanged(val value: LocalTime): AddTimeEntryUiEvent()
        data class TimeEntryProjectChanged(val value: String): AddTimeEntryUiEvent()
        data class TimeEntryDescriptionChanged(val value: String): AddTimeEntryUiEvent()

        object newTimeEntry: AddTimeEntryUiEvent()
}

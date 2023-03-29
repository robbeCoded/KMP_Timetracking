package de.cgi.android.timetracking

sealed class AddTimeEntryUiEvent {
        data class TimeEntryDayChanged(val value: String): AddTimeEntryUiEvent()
        data class TimeEntryDurationChanged(val value: String): AddTimeEntryUiEvent()
        data class TimeEntryStartChanged(val value: String): AddTimeEntryUiEvent()
        data class TimeEntryEndChanged(val value: String): AddTimeEntryUiEvent()
        data class TimeEntryProjectChanged(val value: String): AddTimeEntryUiEvent()
        data class TimeEntryDescriptionChanged(val value: String): AddTimeEntryUiEvent()

        object newTimeEntry: AddTimeEntryUiEvent()
}

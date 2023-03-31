package de.cgi.android.timetracking.addedittimeentry

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed class AddEditTimeEnryUIEvent {
        data class TimeEntryDayChanged(val value: LocalDate): AddEditTimeEnryUIEvent()
        data class TimeEntryDurationChanged(val value: LocalTime): AddEditTimeEnryUIEvent()
        data class TimeEntryStartChanged(val value: LocalTime): AddEditTimeEnryUIEvent()
        data class TimeEntryEndChanged(val value: LocalTime): AddEditTimeEnryUIEvent()
        data class TimeEntryProjectChanged(val value: String): AddEditTimeEnryUIEvent()
        data class TimeEntryDescriptionChanged(val value: String): AddEditTimeEnryUIEvent()

        data class EditTimeEntry(val id: String): AddEditTimeEnryUIEvent()

        data class DeleteTimeEntry(val id: String): AddEditTimeEnryUIEvent()

        object SubmitTimeEntry: AddEditTimeEnryUIEvent()
}

package de.cgi.android.timetracking.addedittimeentry

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class AddEditTimeEntryState(
    val isLoading: Boolean = false,
    val day: LocalDate = LocalDate(1,1,1),
    val duration: LocalTime = LocalTime(0,0),
    val start: LocalTime = LocalTime(0,0),
    val end: LocalTime = LocalTime(0,0),
    val description: String = "",
    val project: String = "641d6db09c10b647af336552" //TODO: remove, just for testing
)

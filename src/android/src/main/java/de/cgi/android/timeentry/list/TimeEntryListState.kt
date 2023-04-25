package de.cgi.android.timeentry.list

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import kotlinx.datetime.LocalTime

data class TimeEntryListState (
    val timeEntryListState: ResultState<List<TimeEntry>> = ResultState.Loading,
    val projectMapState: ResultState<Map<String, String>> = ResultState.Loading,
    val totalDuration: LocalTime = LocalTime(0,0,0)
)

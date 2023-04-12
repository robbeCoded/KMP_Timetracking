package de.cgi.android.timeentry

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry

data class TimeEntryListState (
    val timeEntryListState: ResultState<List<TimeEntry>> = ResultState.Loading,
    val projectMapState: ResultState<Map<String, String>> = ResultState.Loading,
    val removeTimeEntryState: ResultState<Unit>? = null
)

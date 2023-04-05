package de.cgi.android.timetracking

import de.cgi.common.ErrorEntity
import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry

data class TimeEntryListState (
    val timeEntryListState: ResultState<List<TimeEntry>> = ResultState.Loading,
    val removeTimeEntryState: ResultState<Unit>? = null
)

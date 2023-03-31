package de.cgi.android

import de.cgi.common.data.model.TimeEntry

data class TimeEntryState(
    val isLoading: Boolean = false,
    val timeEntries: List<TimeEntry> = emptyList()
)
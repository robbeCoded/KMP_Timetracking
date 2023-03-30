package de.cgi.android

import de.cgi.common.data.model.responses.TimeEntryResponse

data class TimeEntryState(
    val isLoading: Boolean = false,
    val timeEntries: List<TimeEntryResponse> = emptyList()
)
package de.cgi.android.timetracking

data class AddTimeEntryState(
    val isLoading: Boolean = false,
    val day: String = "",
    val duration: String = "",
    val start: String = "",
    val end: String = "",
    val description: String = "",
    val project: String = "",
)

package de.cgi.common.timeentry

import de.cgi.common.data.model.TimeEntry
import de.cgi.common.projects.ProjectListState
import de.cgi.common.util.ResultState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class TimeEntryViewState(
    val timeEntryEditState: ResultState<TimeEntry?> = ResultState.Loading,
    val timeEntryDeleteState: ResultState<Boolean> = ResultState.Loading,
    val timeEntryFetchState: ResultState<TimeEntry?> = ResultState.Loading,
    val listState: ProjectListState = ProjectListState(),
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val duration: LocalTime? = null,
    val date: LocalDate? = null,
    val description: String? = null,
    val projectId: String? = null,
    val projectName: String? = null
)

package de.cgi.common.timeentry

import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.projects.ProjectListState
import de.cgi.common.util.ResultState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime


expect class TimeEntryAddViewModel(
    addUseCase: TimeEntryAddUseCase,
    getProjectsUseCase: GetProjectsUseCase,
    userRepository: UserRepository
) {
    val date: StateFlow<LocalDate?>
    val startTime: StateFlow<LocalTime?>
    val endTime: StateFlow<LocalTime?>
    val duration: StateFlow<LocalTime?>
    val description: StateFlow<String?>
    val projectId: StateFlow<String?>
    val projectName: StateFlow<String?>
    val timeEntryAddState: StateFlow<ResultState<TimeEntry?>>
    val listState: StateFlow<ProjectListState>

    fun submitTimeEntry()
    fun getProjects()

    fun dateChanged(date: LocalDate)
    fun startTimeChanged(startTime: LocalTime)
    fun endTimeChanged(endTime: LocalTime)
    fun durationChanged(duration: LocalTime)
    fun descriptionChanged(description: String)
    fun projectChanged(projectId: String, projectName: String)

    fun getStartTime(): LocalTime?
    fun getEndTime(): LocalTime?
    fun getDuration(): LocalTime?
    fun getDate(): LocalDate?
    fun getDescription(): String?
    fun getProjectId(): String?
    fun getProjectName(): String?
}

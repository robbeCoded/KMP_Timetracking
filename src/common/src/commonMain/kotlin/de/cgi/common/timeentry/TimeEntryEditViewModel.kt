package de.cgi.common.timeentry

import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.projects.ProjectListState
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.util.ResultState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

expect class TimeEntryEditViewModel(
    editUseCase: TimeEntryEditUseCase,
    projectMapProvider: ProjectMapProvider,
    userRepository: UserRepository,
    timeEntryId: String
) {
    val timeEntryEditState: StateFlow<ResultState<TimeEntry?>>
    val timeEntryDeleteState: StateFlow<ResultState<Boolean>>
    val timeEntryFetchState: StateFlow<ResultState<TimeEntry?>>
    val listState: StateFlow<ProjectListState>
    val startTime: StateFlow<LocalTime?>
    val endTime: StateFlow<LocalTime?>
    val duration: StateFlow<LocalTime?>
    val date: StateFlow<LocalDate?>
    val description: StateFlow<String?>
    val projectId: StateFlow<String?>
    val projectName: StateFlow<String?>

    fun updateTimeEntry()
    fun deleteTimeEntry()
    fun getTimeEntryById()
    fun updateValues(timeEntry: TimeEntry)

    fun startTimeChanged(startTime: LocalTime)
    fun endTimeChanged(endTime: LocalTime)
    fun durationChanged(duration: LocalTime)
    fun dateChanged(date: LocalDate)
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

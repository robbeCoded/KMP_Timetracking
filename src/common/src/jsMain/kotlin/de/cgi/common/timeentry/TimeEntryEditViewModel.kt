package de.cgi.common.timeentry

import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.projects.ProjectListState
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.util.ResultState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

actual class TimeEntryEditViewModel actual constructor(
    editUseCase: TimeEntryEditUseCase,
    projectMapProvider: ProjectMapProvider,
    userRepository: UserRepository
) {
    actual val timeEntryEditState: StateFlow<ResultState<TimeEntry?>>
        get() = TODO("Not yet implemented")
    actual val timeEntryDeleteState: StateFlow<ResultState<Boolean>>
        get() = TODO("Not yet implemented")
    actual val timeEntryFetchState: StateFlow<ResultState<TimeEntry?>>
        get() = TODO("Not yet implemented")
    actual val listState: StateFlow<ProjectListState>
        get() = TODO("Not yet implemented")
    actual val startTime: StateFlow<LocalTime?>
        get() = TODO("Not yet implemented")
    actual val endTime: StateFlow<LocalTime?>
        get() = TODO("Not yet implemented")
    actual val duration: StateFlow<LocalTime?>
        get() = TODO("Not yet implemented")
    actual val date: StateFlow<LocalDate?>
        get() = TODO("Not yet implemented")
    actual val description: StateFlow<String?>
        get() = TODO("Not yet implemented")
    actual val projectId: StateFlow<String?>
        get() = TODO("Not yet implemented")
    actual val projectName: StateFlow<String?>
        get() = TODO("Not yet implemented")

    actual fun updateTimeEntry() {
    }

    actual fun deleteTimeEntry() {
    }

    actual fun getTimeEntryById() {
    }

    actual fun startTimeChanged(startTime: LocalTime) {
    }

    actual fun endTimeChanged(endTime: LocalTime) {
    }

    actual fun durationChanged(duration: LocalTime) {
    }

    actual fun dateChanged(date: LocalDate) {
    }

    actual fun descriptionChanged(description: String) {
    }

    actual fun projectChanged(projectId: String, projectName: String) {
    }

    actual fun getStartTime(): LocalTime? {
        TODO("Not yet implemented")
    }

    actual fun getEndTime(): LocalTime? {
        TODO("Not yet implemented")
    }

    actual fun getDuration(): LocalTime? {
        TODO("Not yet implemented")
    }

    actual fun getDate(): LocalDate? {
        TODO("Not yet implemented")
    }

    actual fun getDescription(): String? {
        TODO("Not yet implemented")
    }

    actual fun getProjectId(): String? {
        TODO("Not yet implemented")
    }

    actual fun getProjectName(): String? {
        TODO("Not yet implemented")
    }

    actual fun updateValues(timeEntry: TimeEntry) {
    }

}
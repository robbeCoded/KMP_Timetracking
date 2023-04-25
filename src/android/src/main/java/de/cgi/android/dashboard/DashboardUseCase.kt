package de.cgi.android.dashboard

import de.cgi.common.ResultState
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime

class DashboardUseCase(
    private val teamRepository: TeamRepository,

) {
    fun getAllTeamsForUser(userId: String): Flow<ResultState<List<Team>>> {
        return teamRepository.getTeamsForUser(userId)
    }
    fun processData(timeEntries: List<TimeEntry>): List<DashboardData> {
        val groupedByProject = timeEntries.groupBy { it.projectId }
        val projectDurations = mutableMapOf<String, LocalTime>()
        var noProjectDuration = LocalTime(0, 0)

        groupedByProject.forEach { (projectId, entries) ->

            var projectDuration = LocalTime(0, 0)
            entries.forEach { timeEntry ->
                val startTime = timeEntry.startTime.toLocalTime()
                val endTime = timeEntry.endTime.toLocalTime()
                projectDuration = LocalTime(
                    endTime.hour.minus(startTime.hour),
                    endTime.minute.minus(startTime.minute)
                )
            }
            if (projectId != null) {
                projectDurations[projectId] = projectDuration
            } else {
                noProjectDuration = LocalTime(
                    noProjectDuration.hour.plus(projectDuration.hour),
                    noProjectDuration.minute.plus(projectDuration.minute)
                )
            }
        }

        val totalDuration =
            projectDurations.values.sumOf { it.toSecondOfDay() } + noProjectDuration.toSecondOfDay()

        val projectSummaries = projectDurations.map { (projectId, duration) ->
            DashboardData(
                projectId,
                duration,
                (duration.toSecondOfDay().toDouble() / totalDuration) * 100
            )
        }.toMutableList()

        // Add an entry for noProjectDuration if it is not zero.
        if (noProjectDuration != LocalTime(0, 0)) {
            projectSummaries.add(
                DashboardData(
                    null,
                    noProjectDuration,
                    (noProjectDuration.toSecondOfDay().toDouble() / totalDuration) * 100
                )
            )
        }

        return projectSummaries.sortedByDescending { it.duration.toSecondOfDay() }
    }
}
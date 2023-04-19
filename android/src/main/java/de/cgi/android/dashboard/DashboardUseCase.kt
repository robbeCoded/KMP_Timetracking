package de.cgi.android.dashboard

import de.cgi.common.data.model.TimeEntry
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime

class DashboardUseCase {
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
        }

        val noProjectSummary = DashboardData(
            null,
            noProjectDuration,
            (noProjectDuration.toSecondOfDay().toDouble() / totalDuration) * 100
        )

        return projectSummaries + noProjectSummary
    }
}
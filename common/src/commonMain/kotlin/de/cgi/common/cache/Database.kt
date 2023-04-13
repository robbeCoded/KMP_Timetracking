package de.cgi.common.cache

import de.cgi.common.data.model.Project
import de.cgi.common.data.model.TimeEntry
import de.cgi.shared.cache.AppDatabase

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun createTimeEntries(timeEntries: List<TimeEntry>) {
        dbQuery.transaction {
            timeEntries.forEach { timeEntry ->
                dbQuery.insertTimeEntry(
                    id = timeEntry.id,
                    timestamp = timeEntry.timestamp,
                    date = timeEntry.date,
                    start_time = timeEntry.startTime,
                    end_time = timeEntry.endTime,
                    project_id = timeEntry.projectId,
                    description = timeEntry.description,
                    user_id = timeEntry.userId
                )
            }
        }
    }

    internal fun insertTimeEntry(timeEntry: TimeEntry) {
        dbQuery.transaction {
            dbQuery.insertTimeEntry(
                id = timeEntry.id,
                timestamp = timeEntry.timestamp,
                date = timeEntry.date,
                start_time = timeEntry.startTime,
                end_time = timeEntry.endTime,
                project_id = timeEntry.projectId,
                description = timeEntry.description,
                user_id = timeEntry.userId
            )
        }
    }

    internal fun updateTimeEntry(timeEntry: TimeEntry) {
        dbQuery.transaction {
            dbQuery.updateTimeEntry(
                id = timeEntry.id,
                timestamp = timeEntry.timestamp,
                date = timeEntry.date,
                start_time = timeEntry.startTime,
                end_time = timeEntry.endTime,
                project_id = timeEntry.projectId,
                description = timeEntry.description,
                user_id = timeEntry.userId
            )
        }
    }




    internal fun clearTimeEntries() {
        dbQuery.transaction {
            dbQuery.clearTimeEntries()
        }
    }

    internal fun deleteTimeEntry(id: String) {
        dbQuery.transaction {
            dbQuery.deleteTimeEntryById(id)
        }
    }

    internal fun getAllTimeEntries(userId: String, date: String): List<TimeEntry> {
        return dbQuery.selectAllTimeEntries(userId, date, ::mapToTimeEntry).executeAsList()
    }

    private fun mapToTimeEntry(
        id: String?,
        timestamp: String?,
        date: String?,
        startTime: String?,
        endTime: String?,
        projectId: String?,
        description: String?,
        userId: String?
    ): TimeEntry {
        if (date == null || timestamp == null || startTime == null || endTime == null || id == null || userId == null) {
            throw IllegalStateException("Required field is null")
        }

        return TimeEntry(
            id = id,
            timestamp = timestamp,
            date = date,
            startTime = startTime,
            endTime = endTime,
            projectId = projectId,
            description = description,
            userId = userId
        )
    }

    internal fun getTimeEntryById(id: String): TimeEntry? {
       val result: TimeEntry? = try {
           val query = dbQuery.selectTimeEntryById(id).executeAsOne()
           TimeEntry(
               date = query.date,
               timestamp = query.timestamp,
               startTime = query.start_time,
               endTime = query.end_time,
               id = query.id,
               projectId = query.project_id,
               description = query.description,
               userId = query.user_id
           )
       } catch (e: Exception){
           null
       }
        return result
    }




    internal fun createProjects(projects: List<Project>) {
        dbQuery.transaction {
            projects.forEach { project ->
                dbQuery.insertProject(
                    id = project.id,
                    name = project.name,
                    startDate = project.startDate,
                    endDate = project.endDate,
                    description = project.description,
                    userId = project.userId
                )
            }
        }
    }

    internal fun insertProject(project: Project) {
        dbQuery.transaction {
            dbQuery.insertProject(
                id = project.id,
                name = project.name,
                startDate = project.startDate,
                endDate = project.endDate,
                description = project.description,
                userId = project.userId
            )
        }
    }

    internal fun updateProject(project: Project) {
        dbQuery.transaction {
            dbQuery.updateProject(
                id = project.id,
                name = project.name,
                startDate = project.startDate,
                endDate = project.endDate,
                description = project.description,
                userId = project.userId
            )
        }
    }




    internal fun clearProjects() {
        dbQuery.transaction {
            dbQuery.clearProjects()
        }
    }

    internal fun deleteProject(id: String) {
        dbQuery.transaction {
            dbQuery.deleteProjectById(id)
        }
    }

    internal fun getAllProjects(userId: String): List<Project> {
        return dbQuery.selectAllProjects(userId, ::mapToProject).executeAsList()
    }

    private fun mapToProject(
        id: String?,
        name: String?,
        startDate: String?,
        endDate: String?,
        description: String?,
        userId: String?
    ): Project {
        if (name == null || startDate == null || endDate == null || id == null || userId == null) {
            throw IllegalStateException("Required field is null")
        }

        return Project(
            id = id,
            name = name,
            startDate = startDate,
            endDate = endDate,
            description = description,
            userId = userId
        )
    }

    internal fun getProjectById(id: String): Project? {
        val result: Project? = try {
            val query = dbQuery.selectProjectById(id).executeAsOne()
            Project(
                id = query.id,
                name = query.name,
                description = query.description,
                startDate =  query.startDate,
                endDate = query.endDate,
                userId = query.userId,
            )
        } catch (e: Exception){
            null
        }
        return result
    }
}
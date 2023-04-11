package de.cgi.common.cache

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

    internal fun getAllTimeEntries(): List<TimeEntry> {
        return dbQuery.selectAllTimeEntries(::mapToTimeEntry).executeAsList()
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
               query.date,
               query.timestamp,
               query.start_time,
               query.end_time,
               query.id,
               query.project_id,
               query.description,
               query.user_id
           )
       } catch (e: Exception){
           null
       }
        return result
    }

}
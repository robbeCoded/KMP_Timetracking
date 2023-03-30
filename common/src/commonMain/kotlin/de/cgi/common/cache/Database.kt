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
                    timeEntry.id,
                    timeEntry.timestamp,
                    timeEntry.startTime,
                    timeEntry.endTime,
                    timeEntry.projectId,
                    timeEntry.description,
                    timeEntry.userId
                )
            }
        }
    }

    internal fun createTimeEntryById(timeEntry: TimeEntry) {
        dbQuery.transaction {
            dbQuery.insertTimeEntry(
                timeEntry.id,
                timeEntry.timestamp,
                timeEntry.startTime,
                timeEntry.endTime,
                timeEntry.projectId,
                timeEntry.description,
                timeEntry.userId
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
        timestamp: String,
        startTime: String,
        endTime: String,
        id: String,
        projectId: String?,
        description: String?,
        userId: String
    ): TimeEntry {
        return TimeEntry(
            timestamp = timestamp,
            startTime = startTime,
            endTime = endTime,
            id = id,
            projectId = projectId,
            description = description,
            userId = userId
        )
    }

    internal fun getTimeEntryById(id: String): TimeEntry? {
       val result: TimeEntry? = try {
           val query = dbQuery.selectTimeEntryById(id).executeAsOne()
           TimeEntry(
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
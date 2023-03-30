package de.cgi.common.cache

import de.cgi.common.data.model.TimeEntry
import de.cgi.shared.cache.AppDatabase
import kotlinx.datetime.LocalDateTime

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun deleteTimeEntry(id: String) {
        dbQuery.transaction {
            dbQuery.deleteTimeEntryById(id)
        }
    }

    internal fun getAllTimeEntries() {
        dbQuery.transaction {
            dbQuery.selectAllTimeEntries()
        }
    }

    internal fun getTimeEntryById(id: String) {
        dbQuery.transaction {
            dbQuery.selectTimeEntryById(id)
        }
    }

}
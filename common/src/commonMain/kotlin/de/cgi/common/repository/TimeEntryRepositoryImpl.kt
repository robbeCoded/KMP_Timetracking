package de.cgi.common.repository

import de.cgi.common.api.TimeEntryApi
import de.cgi.common.cache.Database
import de.cgi.common.cache.DatabaseDriverFactory
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.requests.TimeEntryRequest
import io.ktor.client.call.*
import io.ktor.http.*

class TimeEntryRepositoryImpl(
    databaseDriverFactory: DatabaseDriverFactory,
    private val api: TimeEntryApi
) : TimeEntryRepository {

    private val database = Database(databaseDriverFactory)
    override suspend fun newTimeEntry(
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?,
        token: String
    ): TimeEntry? {
        val timeEntry = NewTimeEntry(startTime, endTime, userId, description, projectId)
        return api.newTimeEntry(timeEntry, token)?.also {
            database.createTimeEntry(it)
        }
    }


    override suspend fun getTimeEntries(token: String, forceReload: Boolean): List<TimeEntry> {
        val cachedTimeEntries = database.getAllTimeEntries()
        return if (cachedTimeEntries.isNotEmpty() && !forceReload) {
            cachedTimeEntries
        } else {
            api.getTimeEntries(token).also {
                database.clearTimeEntries()
                database.createTimeEntries(it)
            }
        }
    }

    override suspend fun getTimeEntryById(
        id: String,
        token: String,
        forceReload: Boolean
    ): TimeEntry? {
        val cachedTimeEntry = database.getTimeEntryById(id)
        val timeEntryRequest = TimeEntryRequest(token = token, id = id)
        return if (cachedTimeEntry != null && !forceReload) {
            cachedTimeEntry
        } else {
            val result = api.getTimeEntryById(timeEntryRequest)
            if (result.status == HttpStatusCode.NoContent) {
                val timeEntry = result.body<TimeEntry>()
                database.deleteTimeEntry(id)
                database.createTimeEntry(timeEntry)
                timeEntry
            } else {
                null
            }
        }
    }


    override suspend fun deleteTimeEntry(id: String, token: String): Boolean {
        val result = api.deleteTimeEntry(
            TimeEntryRequest(
                token = token,
                id = id
            )
        ).status == HttpStatusCode.NoContent
        if (result) {
            database.deleteTimeEntry(id)
        }
        return result
    }
}
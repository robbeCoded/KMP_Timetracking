package de.cgi.common.repository

import de.cgi.common.api.TimeEntryApi
import de.cgi.common.cache.Database
import de.cgi.common.cache.DatabaseDriverFactory

import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.TimeEntryResponse
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
        projectId: String?, token: String
    ): AuthResult<TimeEntryResponse?> {

        val timeEntry = NewTimeEntry(startTime, endTime, userId, description, projectId)
        val response = api.newTimeEntry(timeEntry, token)

        return when (response.status) {
            HttpStatusCode.OK -> {
                AuthResult.Authorized(response.body())
            }
            HttpStatusCode.Unauthorized -> {
                AuthResult.Unauthorized()
            }
            else -> {
                AuthResult.UnknownError()
            }
        }
    }


    override suspend fun getTimeEntries(token: String, forceReload: Boolean): List<TimeEntry> {
        val cachedTimeEntries = database.getAllTimeEntries()
        println("INSIDE THE GET TIME ENTRIES API FUN")
        return if (cachedTimeEntries.isNotEmpty() && !forceReload) {
            println("INSIDE THE IF")
            cachedTimeEntries
        } else {
            api.getTimeEntries(token).also {
                println("INSIDE THE ELSE")
                database.clearTimeEntries()
                database.createTimeEntries(it)
            }
        }
    }

    override suspend fun getTimeEntryById(id: String,token: String, forceReload: Boolean): TimeEntry? {
        val cachedTimeEntry = database.getTimeEntryById(id)
        return if (cachedTimeEntry != null && !forceReload) {
            cachedTimeEntry
        } else {
            api.getTimeEntryById(id,token)?.also {
                database.deleteTimeEntry(id)
                database.createTimeEntryById(it)
            }
        }
    }

    override suspend fun deleteTimeEntry(id: String, token: String): AuthResult<Boolean> {
        val response = api.deleteTimeEntry(id, token)
        return when (response.status) {
            HttpStatusCode.OK -> {
                AuthResult.Authorized(response.body())
            }
            HttpStatusCode.Unauthorized -> {
                AuthResult.Unauthorized()
            }
            else -> {
                AuthResult.UnknownError()
            }
        }
    }
}
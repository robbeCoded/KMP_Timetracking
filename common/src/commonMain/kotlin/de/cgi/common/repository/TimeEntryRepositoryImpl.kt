package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.api.TimeEntryApi
import de.cgi.common.cache.Database
import de.cgi.common.cache.DatabaseDriverFactory
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.requests.TimeEntryRequest
import de.cgi.common.data.model.requests.UpdateTimeEntryRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class TimeEntryRepositoryImpl(
    databaseDriverFactory: DatabaseDriverFactory,
    private val api: TimeEntryApi
) : TimeEntryRepository {

    private val database = Database(databaseDriverFactory)
    override fun newTimeEntry(
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?
    ): Flow<ResultState<TimeEntry?>> {
        val timeEntry = NewTimeEntry(date, startTime, endTime, userId, description, projectId)
        return api.newTimeEntry(timeEntry).map { result ->
            if (result is ResultState.Success) {
                result.data?.let { database.insertTimeEntry(it) }
            }
            result
        }
    }

    override fun updateTimeEntry(
        id: String,
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?
    ): Flow<ResultState<TimeEntry?>> {
        val timeEntry =
            UpdateTimeEntryRequest(id, date, startTime, endTime, projectId, description, userId)
        return api.updateTimeEntry(timeEntry).map { result ->
            if (result is ResultState.Success) {
                result.data?.let { database.updateTimeEntry(it) }
            }
            result
        }
    }

    override fun getTimeEntriesForWeek(userId: String, startDate: String, forceReload: Boolean): Flow<ResultState<List<TimeEntry>>> {
        val startLocalDate = LocalDate.parse(startDate)
        val endLocalDate = startLocalDate.plus(
            6,
            DateTimeUnit.DAY
        )
        val endDate = endLocalDate.toString()
        val cachedTimeEntries = database.getTimeEntriesForWeek(userId, startDate, endDate)
        return if (cachedTimeEntries.isNotEmpty() && !forceReload) {
            flowOf(ResultState.Success(cachedTimeEntries))
        } else {
            api.getTimeEntries(userId, startDate).map { result ->
                if (result is ResultState.Success) {
                    database.clearTimeEntries()
                    database.createTimeEntries(result.data)
                }
                result
            }
        }
    }

    override fun getTimeEntryById(
        id: String,
        forceReload: Boolean
    ): Flow<ResultState<TimeEntry?>> {
        val cachedTimeEntry = database.getTimeEntryById(id)
        val timeEntryRequest = TimeEntryRequest(id = id)
        return if (cachedTimeEntry != null && !forceReload) {
            flowOf(ResultState.Success(cachedTimeEntry))
        } else {
            api.getTimeEntryById(timeEntryRequest).map { result ->
                if (result is ResultState.Success && result.data != null) {
                    database.deleteTimeEntry(id)
                    database.insertTimeEntry(result.data)
                }
                result
            }
        }
    }


    override fun deleteTimeEntry(id: String): Flow<ResultState<Boolean>> {
        return api.deleteTimeEntry(
            TimeEntryRequest(
                id = id
            )
        ).map { result ->
            if (result is ResultState.Success && result.data) {
                database.deleteTimeEntry(id)
            }
            result
        }
    }
}

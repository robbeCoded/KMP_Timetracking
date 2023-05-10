package de.cgi.common.repository

import de.cgi.common.util.ResultState
import de.cgi.common.api.TimeEntryApi
//import de.cgi.common.cache.Database
//import de.cgi.common.cache.DatabaseDriverFactory
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.requests.TeamTimeEntriesRequest
import de.cgi.common.data.model.requests.TimeEntryRequest
import de.cgi.common.data.model.requests.UpdateTimeEntryRequest
import kotlinx.coroutines.flow.Flow

class TimeEntryRepositoryImpl(
    //databaseDriverFactory: DatabaseDriverFactory,
    private val api: TimeEntryApi
) : TimeEntryRepository {

    //private val database = Database(databaseDriverFactory)
    override fun newTimeEntry(
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?
    ): Flow<ResultState<TimeEntry?>> {
        val timeEntry = NewTimeEntry(date, startTime, endTime, userId, description, projectId)
        return api.newTimeEntry(timeEntry)
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
        return api.updateTimeEntry(timeEntry)
    }

    override fun getTimeEntriesForWeek(
        userId: String,
        startDate: String,
        forceReload: Boolean
    ): Flow<ResultState<List<TimeEntry>>> {
        return api.getTimeEntries(userId, startDate)
    }

    override fun getTeamTimeEntriesForWeek(
        request: TeamTimeEntriesRequest,
        forceReload: Boolean
    ): Flow<ResultState<List<List<TimeEntry>?>>> {
        println("HELLO FROM REPO GET TEAM TIME ETNRIES")
        return api.getTeamTimeEntries(request)
    }

    override fun getTimeEntryById(
        id: String,
        forceReload: Boolean
    ): Flow<ResultState<TimeEntry?>> {
        val timeEntryRequest = TimeEntryRequest(id = id)
        return api.getTimeEntryById(timeEntryRequest)
    }


    override fun deleteTimeEntry(id: String): Flow<ResultState<Boolean>> {
        return api.deleteTimeEntry(
            TimeEntryRequest(
                id = id
            )
        )
    }
}

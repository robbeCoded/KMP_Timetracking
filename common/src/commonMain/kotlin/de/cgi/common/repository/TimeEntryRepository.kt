package de.cgi.common.repository

import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.TimeEntryResponse

interface TimeEntryRepository {
    suspend fun newTimeEntry(
        startTime: String,
        endTime: String,
        userId: String,
        description: String?,
        projectId: String?, token: String
    ): AuthResult<TimeEntryResponse?>

    suspend fun getTimeEntries(token: String): AuthResult<List<TimeEntryResponse>>
    suspend fun getTimeEntryById(id: String, token: String): AuthResult<TimeEntryResponse?>
    suspend fun deleteTimeEntry(id: String, token: String): AuthResult<Boolean>
}
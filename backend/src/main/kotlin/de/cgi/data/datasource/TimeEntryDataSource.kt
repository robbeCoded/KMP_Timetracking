package de.cgi.data.datasource

import de.cgi.data.models.TimeEntry
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

interface TimeEntryDataSource {
    suspend fun insertTimeEntry(timeEntry: TimeEntry): Boolean
    suspend fun updateTimeEntry(timeEntry: TimeEntry): Boolean
    suspend fun getTimeEntries(userId: ObjectId): List<TimeEntry>
    suspend fun getTimeEntriesForDate(userId: ObjectId, date: String): List<TimeEntry>
    suspend fun getTimeEntryById(id: ObjectId): TimeEntry?
    suspend fun deleteTimeEntry(id: ObjectId): Boolean

}
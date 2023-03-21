package de.cgi.data.datasource

import de.cgi.data.models.TimeEntry
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase

class MongoTimeEntryDataSource(
    db: CoroutineDatabase
): TimeEntryDataSource {

    val timeEntries = db.getCollection<TimeEntry>()
    override suspend fun insertTimeEntry(timeEntry: TimeEntry): Boolean {
        return timeEntries.insertOne(timeEntry).wasAcknowledged()
    }

    override suspend fun getTimeEntries(): List<TimeEntry> {
        return timeEntries.find().toList()
    }

    override suspend fun getTimeEntryById(id: ObjectId): TimeEntry? {
        return timeEntries.findOneById(id)
    }

    override suspend fun deleteTimeEntry(id: ObjectId): Boolean {
        return timeEntries.deleteOneById(id).wasAcknowledged()
    }
}
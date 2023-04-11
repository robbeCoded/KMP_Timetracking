package de.cgi.data.datasource

import com.mongodb.client.model.UpdateOptions
import de.cgi.data.models.TimeEntry
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne

class MongoTimeEntryDataSource(
    db: CoroutineDatabase
) : TimeEntryDataSource {

    private val timeEntries = db.getCollection<TimeEntry>()
    override suspend fun insertTimeEntry(timeEntry: TimeEntry): Boolean {
        return timeEntries.insertOne(timeEntry).wasAcknowledged()
    }

    override suspend fun updateTimeEntry(timeEntry: TimeEntry): Boolean {
        val filter = TimeEntry::id eq timeEntry.id
        val update = set(
            TimeEntry::timestamp.setTo(timeEntry.timestamp),
            TimeEntry::date.setTo(timeEntry.date),
            TimeEntry::startTime.setTo(timeEntry.startTime),
            TimeEntry::endTime.setTo(timeEntry.endTime),
            TimeEntry::projectId.setTo(timeEntry.projectId),
            TimeEntry::description.setTo(timeEntry.description),
            TimeEntry::userId.setTo(timeEntry.userId)
        )
        val updateResult = timeEntries.updateOne(filter, update)
        return updateResult.wasAcknowledged()
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
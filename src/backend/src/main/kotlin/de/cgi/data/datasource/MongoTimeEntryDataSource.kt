package de.cgi.data.datasource

import de.cgi.data.models.TimeEntry
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase

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


    override suspend fun getTimeEntries(userId: ObjectId): List<TimeEntry> {
        return timeEntries.find(TimeEntry::userId eq userId).toList()
    }

    override suspend fun getTimeEntriesForDate(userId: ObjectId, date: String): List<TimeEntry> {
        return timeEntries.find(
            and(
                TimeEntry::userId eq userId,
                TimeEntry::date eq date
            )
        )
            .toList()
    }

    override suspend fun getTimeEntriesForWeek(
        userId: ObjectId,
        startDate: String
    ): List<TimeEntry> {
        val startLocalDate = LocalDate.parse(startDate)
        val endLocalDate = startLocalDate.plus(
            6,
            DateTimeUnit.DAY
        )
        val allUserEntries = timeEntries.find(TimeEntry::userId eq userId).toList()

        val filteredTimeEntries = allUserEntries.filter { entry ->
            val entryLocalDate = LocalDate.parse(entry.date)
            entryLocalDate in startLocalDate..endLocalDate
        }

        return filteredTimeEntries
    }


    override suspend fun getTimeEntryById(id: ObjectId): TimeEntry? {
        return timeEntries.findOneById(id)
    }

    override suspend fun deleteTimeEntry(id: ObjectId): Boolean {
        return timeEntries.deleteOneById(id).wasAcknowledged()
    }
}
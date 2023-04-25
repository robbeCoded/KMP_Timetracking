package de.cgi.android.timeentry.list


import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime


class TimeEntryListUseCase (
    private val repository: TimeEntryRepository
) {
    fun getTimeEntries(userId: String, date: String, forceReload: Boolean): Flow<ResultState<List<TimeEntry>>> {
        return repository.getTimeEntriesForWeek(userId = userId, startDate = date, forceReload = forceReload)
    }

    fun calculateTotalDuration(timeEntries: List<TimeEntry>, date: LocalDate): LocalTime {
        var totalDurationInSeconds = 0L
        timeEntries.forEach { timeEntry ->
            if (LocalDate.parse(timeEntry.date) == date) {
                val startTime = timeEntry.startTime.toLocalTime()
                val endTime = timeEntry.endTime.toLocalTime()
                val durationInSeconds = endTime.toSecondOfDay() - startTime.toSecondOfDay()
                totalDurationInSeconds += durationInSeconds
            }
        }
        return LocalTime.fromSecondOfDay(totalDurationInSeconds.toInt())
    }

}

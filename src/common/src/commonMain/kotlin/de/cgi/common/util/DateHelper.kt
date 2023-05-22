package de.cgi.common.util

import io.ktor.util.date.*
import kotlinx.datetime.*

fun getWeekStartDate(date: LocalDate): LocalDate {
    val dayOfWeek = date.dayOfWeek
    val daysToSubtract = dayOfWeek.ordinal - WeekDay.MONDAY.ordinal
    return date.minus(daysToSubtract, DateTimeUnit.DAY)
}

fun getWeekOfYear(date: LocalDate): Int {
    val firstDayOfYear = LocalDate(date.year, 1, 1)
    val firstWeekStart = firstDayOfYear
        .plus(DatePeriod(days = 1 - firstDayOfYear.dayOfWeek.ordinal % 7)) // Find the nearest Monday on or before 1st January

    return if (date < firstWeekStart) {
        getWeekOfYear(LocalDate(date.year - 1, 12, 31))
    } else {
        ((date - firstWeekStart).days / 7) + 1
    }
}

fun getCurrentDateTime(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin"))
}

fun generateWeekDates(selectedDate: LocalDate): List<LocalDate> {
    val startOfWeek =
        selectedDate.minus(DatePeriod(days = (selectedDate.dayOfWeek.ordinal - (DayOfWeek(1).ordinal))))
    return List(7) { startOfWeek.plus(DatePeriod(days = it)) }
}

fun customDateFormatter(date: LocalDate) : String {
    return "${date.dayOfWeek.name}, ${date.month} ${date.dayOfMonth}"
}

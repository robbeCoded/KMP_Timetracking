package de.cgi.android.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekdayHeader(
    currentDate: LocalDate,
    selectedDate: LocalDate,
    totalDuration: LocalTime,
    onDateSelected: (LocalDate) -> Unit,
) {
    val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")
    val weekDates = generateWeekDates(selectedDate)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = customDateFormatter(selectedDate))
            Text(text = totalDuration.toString())
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            daysOfWeek.forEachIndexed { index, day ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onDateSelected(weekDates[index]) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val isCurrentDate = weekDates[index] == currentDate
                    val isSelectedDate = weekDates[index] == selectedDate
                    val backgroundColor = when {
                        isSelectedDate -> Color(0xFFa199ff)
                        isCurrentDate -> Color(0xFFd0ccff)
                        else -> Color.Transparent
                    }

                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(backgroundColor)
                            .padding(horizontal = 15.dp, vertical = 3.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = day)
                            Text(text = weekDates[index].dayOfMonth.toString())
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun generateWeekDates(selectedDate: LocalDate): List<LocalDate> {
    val startOfWeek =
        selectedDate.minus(DatePeriod(days = (selectedDate.dayOfWeek.ordinal - (DayOfWeek(1).ordinal))))
    return List(7) { startOfWeek.plus(DatePeriod(days = it)) }
}

fun customDateFormatter(date: LocalDate) : String {
    return "${date.dayOfWeek.name}, ${date.month} ${date.dayOfMonth}"
}
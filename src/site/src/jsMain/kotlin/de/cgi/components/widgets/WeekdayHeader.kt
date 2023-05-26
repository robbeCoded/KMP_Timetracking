package de.cgi.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import de.cgi.common.util.generateWeekDates
import de.cgi.components.styles.Theme
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text


@Composable
fun WeekdayHeader(
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {

    val daysOfWeek =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val weekDates = generateWeekDates(selectedDate)

    Row(
        modifier = Modifier
            .margin(8.px)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysOfWeek.forEachIndexed { index, day ->

            val isCurrentDate = weekDates[index] == currentDate
            val isSelectedDate = weekDates[index] == selectedDate
            val backgroundColor = when {
                isSelectedDate -> Theme.ActionSecondary.rgb
                isCurrentDate -> Theme.ActionSuperWeak.rgb
                else -> Colors.Transparent
            }

            Box(
                modifier = Modifier
                    .padding(topBottom = 4.px)
                    .width(14.percent)
                    .height(8.percent)
                    .borderRadius(10.px)
                    .backgroundColor(backgroundColor)
                    .onClick { onDateSelected(weekDates[index]) }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(day)
                    Spacer()
                    Text(weekDates[index].dayOfMonth.toString())
                }
            }
        }

    }

}

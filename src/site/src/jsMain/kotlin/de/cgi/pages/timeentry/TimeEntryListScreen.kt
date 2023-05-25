package de.cgi.pages.timeentry

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.layout.Divider
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.theme.shapes.Shape
import com.varabyte.kobweb.silk.theme.shapes.clip
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryListViewModel
import de.cgi.common.util.customDateFormatter
import de.cgi.common.util.format
import de.cgi.common.util.generateWeekDates
import de.cgi.components.layouts.PageLayout
import de.cgi.components.styles.PageHeaderStyle
import de.cgi.components.styles.PageTitle
import de.cgi.components.styles.Theme
import de.cgi.components.util.AsyncData
import de.cgi.components.util.JsJodaTimeZoneModule
import kotlinx.datetime.*
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page(routeOverride = "list")
@Composable
fun TimeEntryListScreen() {
    val di = localDI()
    val viewModel: TimeEntryListViewModel by di.instance()
    val ctx = rememberPageContext()
    val timeZoneModule = JsJodaTimeZoneModule

    LaunchedEffect(viewModel.updateTrigger.value) {
        viewModel.getTimeEntries()
    }

    val timeEntryListState by viewModel.listState.collectAsState()
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val selectedDate = remember { mutableStateOf(currentDate) }
    val totalDuration = viewModel.totalDuration.collectAsState()

    PageLayout(title = "Time Tracking", pageContext = ctx) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {
            Box(
                PageHeaderStyle.toModifier()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    P(PageTitle.toModifier().toAttrs()) {
                        Text("Time Tracking")
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(customDateFormatter(selectedDate.value))
                        Spacer()
                        Text(totalDuration.value.toString())
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.width(55.percent),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    WeekdayHeader(
                        currentDate = currentDate,
                        selectedDate = selectedDate.value,
                        onDateSelected = { newDate ->
                            selectedDate.value = newDate
                            viewModel.selectedDateChanged(newDate)
                        }
                    )

                    AsyncData(resultState = timeEntryListState.timeEntryListState) { timeEntryList ->
                        timeEntryList?.let {
                            if (timeEntryList.isNotEmpty()) {
                                timeEntryList.forEach { timeEntry ->
                                    if (timeEntry.date == selectedDate.value.toString()) {
                                        TimeEntryListItem(
                                            timeEntry = timeEntry,
                                            onClick = { ctx.router.navigateTo("/timeentry/edit/${timeEntry.id}") },
                                        )
                                    }
                                }
                            } else {
                                Text("There are no time entries for this date.")
                            }

                        }
                    }
                }
            }



            Button(onClick = { ctx.router.navigateTo("/timeentry/add") }) {
                Text("Add time entry")
            }

        }
    }
}

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

@Composable
fun TimeEntryListItem(
    timeEntry: TimeEntry,
    onClick: (TimeEntry) -> Unit,
) {
    val di = localDI()
    val startTime: LocalTime = timeEntry.startTime.toLocalTime()
    val endTime: LocalTime = timeEntry.endTime.toLocalTime()
    val projectMapProvider: ProjectMapProvider by di.instance()

    Box(
        modifier = Modifier
            .margin(8.px)
            .padding(8.px)
            .borderRadius(10.px)
            .fillMaxWidth()
            .backgroundColor(Theme.ItemColor.rgb)
            .onClick { onClick(timeEntry) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val description = if (timeEntry.description.isNullOrBlank()) {
                "Keine Beschreibung"
            } else {
                timeEntry.description
            }
            Div(
                Modifier
                    .fontWeight(FontWeight.SemiBold)
                    .toAttrs()
            ){
                Text(description ?: "Keine Beschreibung")
            }

            Spacer()
            Text("$startTime - $endTime")
            Spacer()
            val projectText =
                timeEntry.projectId?.let { projectMapProvider.getProjectNameById(it) }
                    ?: "Internal"
            Text(projectText)
        }
    }
}
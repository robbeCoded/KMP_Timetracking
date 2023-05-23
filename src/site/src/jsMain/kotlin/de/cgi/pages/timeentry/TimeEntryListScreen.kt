package de.cgi.pages.timeentry

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryListViewModel
import de.cgi.common.util.customDateFormatter
import de.cgi.common.util.generateWeekDates
import de.cgi.components.layouts.PageLayout
import de.cgi.components.util.AsyncData
import de.cgi.components.util.JsJodaTimeZoneModule
import kotlinx.datetime.*
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
    PageLayout(title = "Zeiterfassung") {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {
            WeekdayHeader(
                currentDate = currentDate,
                selectedDate = selectedDate.value,
                totalDuration = viewModel::getTotalDuration.invoke(), // You need to calculate the total duration based on your time entries data
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
    totalDuration: LocalTime,
    onDateSelected: (LocalDate) -> Unit,
) {

    val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")
    val weekDates = generateWeekDates(selectedDate)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(customDateFormatter(selectedDate))
            Text(totalDuration.toString())
        }

        Spacer()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            daysOfWeek.forEachIndexed { index, day ->
                Column(
                    modifier = Modifier.onClick { onDateSelected(weekDates[index]) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val isCurrentDate = weekDates[index] == currentDate
                    val isSelectedDate = weekDates[index] == selectedDate
                    val backgroundColor = when {
                        isSelectedDate -> Colors.AliceBlue
                        isCurrentDate -> Colors.Aqua
                        else -> Colors.Transparent
                    }

                    Box {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(day)
                            Text(weekDates[index].dayOfMonth.toString())
                        }
                    }
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
            .fillMaxWidth()
            .onClick { onClick(timeEntry) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(timeEntry.description ?: "Keine Beschreibung")
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
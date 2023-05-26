package de.cgi.pages.timeentry

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryAddViewModel
import de.cgi.common.timeentry.TimeEntryEditViewModel
import de.cgi.common.timeentry.TimeEntryListViewModel
import de.cgi.common.util.customDateFormatter
import de.cgi.components.layouts.PageLayout
import de.cgi.components.styles.PageHeaderStyle
import de.cgi.components.styles.PageTitle
import de.cgi.components.styles.Theme
import de.cgi.components.styles.VerticalSpacer
import de.cgi.components.util.AsyncData
import de.cgi.components.util.JsJodaTimeZoneModule
import de.cgi.components.widgets.TimeEntryAddForm
import de.cgi.components.widgets.TimeEntryEditForm
import de.cgi.components.widgets.TimeEntryListItem
import de.cgi.components.widgets.WeekdayHeader
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
    val timeEntryListViewModel: TimeEntryListViewModel by di.instance()
    val timeEntryAddViewModel: TimeEntryAddViewModel by di.instance()
    val timeEntryEditViewModel: TimeEntryEditViewModel by di.instance()
    val projectMapProvider: ProjectMapProvider by di.instance()
    val ctx = rememberPageContext()
    val timeZoneModule = JsJodaTimeZoneModule

    val showEdit = remember { mutableStateOf(false) }


    LaunchedEffect(timeEntryListViewModel.updateTrigger.value) {
        timeEntryListViewModel.getTimeEntries()
    }

    val timeEntryListState by timeEntryListViewModel.listState.collectAsState()
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val selectedDate = remember { mutableStateOf(currentDate) }
    val totalDuration = timeEntryListViewModel.totalDuration.collectAsState()

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
            VerticalSpacer(8)

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.px)
                        .width(55.percent),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    WeekdayHeader(
                        currentDate = currentDate,
                        selectedDate = selectedDate.value,
                        onDateSelected = { newDate ->
                            selectedDate.value = newDate
                            timeEntryListViewModel.selectedDateChanged(newDate)
                        }
                    )

                    AsyncData(resultState = timeEntryListState.timeEntryListState) { timeEntryList ->
                        timeEntryList?.let {
                            if (timeEntryList.isNotEmpty()) {
                                timeEntryList.forEach { timeEntry ->
                                    if (timeEntry.date == selectedDate.value.toString()) {
                                        TimeEntryListItem(
                                            timeEntry = timeEntry,
                                            onClick = { selectedTimeEntry ->
                                                timeEntryEditViewModel.clear()
                                                timeEntryEditViewModel.timeEntryId =
                                                    selectedTimeEntry.id
                                                timeEntryEditViewModel.getTimeEntryById()
                                                showEdit.value = true
                                            },
                                        )
                                    }
                                }
                            } else {
                                Text("There are no time entries for this date.")
                            }

                        }
                    }
                }

                //Divider
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(5.percent),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .height(90.percent)
                            .width(1.px)
                            .backgroundColor(Theme.Black.rgb)
                    )
                }

                if (showEdit.value) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(40.percent),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TimeEntryEditForm(
                            viewModel = timeEntryEditViewModel,
                            onTimeEntriesUpdated = { timeEntryListViewModel.notifyTimeEntryUpdates() },
                            projectMapProvider = projectMapProvider
                        )
                        Button(
                            modifier = Modifier
                                .margin(8.px)
                                .backgroundColor(Theme.ItemColor.rgb),
                            onClick = {
                                showEdit.value = false
                            }) {
                            Text("New Time Entry")
                        }
                    }

                } else {
                    TimeEntryAddForm(
                        viewModel = timeEntryAddViewModel,
                        onTimeEntriesUpdated = { timeEntryListViewModel.notifyTimeEntryUpdates() },
                        projectMapProvider = projectMapProvider
                    )
                }

            }
        }

    }
}


package de.cgi.android.timeentry.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import de.cgi.android.ui.components.WeekdayHeader
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.util.AsyncData
import de.cgi.android.util.GenericError
import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import kotlinx.datetime.*


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TimeEntryListScreen(
    timeEntryListState: ResultState<List<TimeEntry>>,
    onGetTotalDuration: () -> LocalTime,
    onNewTimeEntryClick: () -> Unit,
    onTimeEntryClick: (TimeEntry) -> Unit,
    reloadTimeEntries: () -> Unit,
    onSelectedDateChanged: (LocalDate) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    val selectedDate = remember { mutableStateOf(currentDate) }



    Scaffold(scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = onNewTimeEntryClick) {
                Icon(imageVector = FeatherIcons.Plus, contentDescription = "Add time entry")
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WeekdayHeader(
                currentDate = currentDate,
                selectedDate = selectedDate.value,
                totalDuration = onGetTotalDuration.invoke(), // You need to calculate the total duration based on your time entries data
                onDateSelected = { newDate ->
                    selectedDate.value = newDate
                    onSelectedDateChanged(newDate)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = 0.5.dp, color = LocalColor.current.black)

            Box(Modifier.padding(it)) {
                AsyncData(resultState = timeEntryListState, errorContent = {
                    GenericError(
                        onDismissAction = reloadTimeEntries
                    )
                }) { timeEntryList ->
                    timeEntryList?.let {
                        if (timeEntryList.isNotEmpty()) {
                            LazyColumn {
                                items(timeEntryList, key = { timeEntry -> timeEntry.id }) { item ->
                                    if (item.date == selectedDate.value.toString()) {
                                        TimeEntryListItem(
                                            onClick = onTimeEntryClick,
                                            timeEntry = item,
                                        )
                                    }
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(all = 10.dp),
                            ) {
                                Text(
                                    text = "There are no time entries for today. \nCreate one by clicking on the +.",

                                    modifier = Modifier.align(
                                        Alignment.Center
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

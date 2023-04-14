package de.cgi.android.timeentry.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import de.cgi.android.ui.components.WeekdayHeader
import de.cgi.android.util.AsyncData
import de.cgi.android.util.GenericError
import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import kotlinx.datetime.*


@RequiresApi(Build.VERSION_CODES.O)
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
    onGetProjectMap: () -> ResultState<Map<String,String>?>
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

            Box(Modifier.padding(it)) {
                AsyncData(resultState = timeEntryListState, errorContent = {
                    GenericError(
                        onDismissAction = reloadTimeEntries
                    )
                }) { timeEntryList ->
                    timeEntryList?.let {
                        LazyColumn {
                            items(timeEntryList, key = { timeEntry -> timeEntry.id }) { item ->
                                TimeEntryListItem(
                                    onClick = onTimeEntryClick,
                                    timeEntry = item,
                                    onGetProjectMap = onGetProjectMap,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

package de.cgi.android.timetracking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import de.cgi.android.util.AsyncData
import de.cgi.android.util.GenericError
import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TimeEntryListScreen(
    timeEntryListState: ResultState<List<TimeEntry>>,
    removeTimeEntryState: ResultState<Unit>?,
    onNewTimeEntryClick: () -> Unit,
    onTimeEntryClick: (TimeEntry) -> Unit,
    onDeleteTimeEntry: (TimeEntry) -> Unit,
    reloadTimeEntries: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar(title = {
            Text(text = "Timetracking")
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = onNewTimeEntryClick) {
            Icon(imageVector = FeatherIcons.Plus, contentDescription = "Add time entry")
        }
    }) {
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
                                onDeleteTimeEntry = onDeleteTimeEntry
                            )
                        }
                    }
                }


            }
        }
    }

}
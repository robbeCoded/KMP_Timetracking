package de.cgi.android.timeentry

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TimeEntryListDestination(
    router: Router,
) {
    val viewModel = getViewModel<TimeEntryListViewModel>()
    val timeEntryListState by viewModel.listState.collectAsState()

    TimeEntryListScreen(
        timeEntryListState = timeEntryListState.timeEntryListState,
        onNewTimeEntryClick = { router.showTimeEntryAdd() },
        onTimeEntryClick = { router.showTimeEntryEdit(it) },
        onDeleteTimeEntry = viewModel::deleteTimeEntry,
        removeTimeEntryState = timeEntryListState.removeTimeEntryState,
        reloadTimeEntries = viewModel::getTimeEntries
    )
}
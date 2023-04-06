package de.cgi.android.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.timeentry.TimeEntryListScreen
import de.cgi.android.timeentry.TimeEntryViewModel
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TimeEntryListDestination(
    router: Router,
) {
    val viewModel = getViewModel<TimeEntryViewModel>()
    val timeEntryListState by viewModel.listState.collectAsState()

    TimeEntryListScreen(
        timeEntryListState = timeEntryListState.timeEntryListState,
        onNewTimeEntryClick = { router.showTimeEntryDetails() },
        onTimeEntryClick = { router.showTimeEntryDetails() },
        onDeleteTimeEntry = viewModel::deleteTimeEntry,
        removeTimeEntryState = timeEntryListState.removeTimeEntryState,
        reloadTimeEntries = viewModel::getTimeEntries
    )
}
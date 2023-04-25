package de.cgi.android.timeentry.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    LaunchedEffect(viewModel.updateTrigger.value) {
        viewModel.getTimeEntries()
    }

    TimeEntryListScreen(
        timeEntryListState = timeEntryListState.timeEntryListState,
        onGetTotalDuration = viewModel::getTotalDuration,
        onNewTimeEntryClick = { router.showTimeEntryAdd() },
        onTimeEntryClick = { router.showTimeEntryEdit(it) },
        reloadTimeEntries = viewModel::getTimeEntries,
        onSelectedDateChanged = viewModel::selectedDateChanged,
    )
}
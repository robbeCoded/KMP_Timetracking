package de.cgi.android.timeentry.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import de.cgi.common.timeentry.TimeEntryListViewModel
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TimeEntryListDestination(
    router: Router,
) {
    val di = localDI()
    val viewModel: TimeEntryListViewModel by di.instance()
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
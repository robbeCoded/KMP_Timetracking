package de.cgi.android.timeentry.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import de.cgi.android.timeentry.ProjectMapViewModel
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TimeEntryListDestination(
    router: Router,
) {
    val viewModel = getViewModel<TimeEntryListViewModel>()
    val timeEntryListState by viewModel.listState.collectAsState()
    val projectMapViewModel = getViewModel<ProjectMapViewModel>()
    TimeEntryListScreen(
        timeEntryListState = timeEntryListState.timeEntryListState,
        onGetTotalDuration = viewModel::getTotalDuration,
        onNewTimeEntryClick = { router.showTimeEntryAdd() },
        onTimeEntryClick = { router.showTimeEntryEdit(it) },
        reloadTimeEntries = viewModel::getTimeEntries,
        onSelectedDateChanged = viewModel::selectedDateChanged,
        onGetProjectMap = projectMapViewModel::getProjectMap,
    )
}
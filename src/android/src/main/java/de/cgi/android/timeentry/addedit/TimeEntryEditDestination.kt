package de.cgi.android.timeentry.addedit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import de.cgi.android.navigation.Router
import de.cgi.android.timeentry.TimeEntryEditRoute
import de.cgi.android.timeentry.list.TimeEntryListViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TimeEntryEditDestination(
    backStackEntry: NavBackStackEntry,
    router: Router,
) {
    val timeEntryId = TimeEntryEditRoute.getTimeEntryId(backStackEntry) ?: "0"
    val editTimeEntry = true

    val viewModel = getViewModel<TimeEntryEditViewModel>(parameters = { parametersOf(timeEntryId) })
    val listViewModel = getViewModel<TimeEntryListViewModel>()
    TimeEntryAddEditScreen(
        onDateChanged = viewModel::dateChanged,
        onStartTimeChanged = viewModel::startTimeChanged,
        onEndTimeChanged = viewModel::endTimeChanged,
        onDurationChanged = viewModel::durationChanged,
        onDescriptionChanged = viewModel::descriptionChanged,
        onProjectChanged = viewModel::projectChanged,
        onSubmitTimeEntry = { },
        onUpdateTimeEntry = viewModel::updateTimeEntry,
        onNavigateBack = { router.backFromAddEdit(listViewModel::notifyTimeEntryUpdates) },
        onDeleteTimeEntry = viewModel::deleteTimeEntry,
        onGetTimeEntryById = viewModel::getTimeEntryById,
        editTimeEntry = editTimeEntry,
        onGetDate = viewModel::getDate,
        onGetDescription = viewModel::getDescription,
        onGetDuration = viewModel::getDuration,
        onGetEndTime = viewModel::getEndTime,
        onGetProjectId = viewModel::getProjectId,
        onGetProjectName = viewModel::getProjectName,
        onGetStartTime = viewModel::getStartTime,
    )
}

package de.cgi.android.timeentry.addedit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import de.cgi.android.navigation.Router
import de.cgi.android.timeentry.list.TimeEntryListViewModel
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TimeEntryAddDestination(
    router: Router
) {
    val editTimeEntry = false
    val viewModel = getViewModel<TimeEntryAddViewModel>()
    val listViewModel = getViewModel<TimeEntryListViewModel>()

    TimeEntryAddEditScreen(
        onDateChanged = viewModel::dateChanged,
        onStartTimeChanged = viewModel::startTimeChanged,
        onEndTimeChanged = viewModel::endTimeChanged,
        onDurationChanged = viewModel::durationChanged,
        onDescriptionChanged = viewModel::descriptionChanged,
        onProjectChanged = viewModel::projectChanged,
        onSubmitTimeEntry = viewModel::submitTimeEntry,
        onUpdateTimeEntry = { },
        onNavigateBack = { router.backFromAddEdit(listViewModel::notifyTimeEntryUpdates) },
        onDeleteTimeEntry = { },
        onGetTimeEntryById = { },
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
package de.cgi.android.timeentry

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import de.cgi.android.navigation.Router
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

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
    val projectListState by viewModel.listState.collectAsState()

    TimeEntryAddEditScreen(
        onDateChanged = viewModel::dateChanged,
        onStartTimeChanged = viewModel::startTimeChanged,
        onEndTimeChanged = viewModel::endTimeChanged,
        onDurationChanged = viewModel::durationChanged,
        onDescriptionChanged = viewModel::descriptionChanged,
        onProjectChanged = viewModel::projectChanged,
        onSubmitTimeEntry = { },
        onUpdateTimeEntry = viewModel::updateTimeEntry,
        onNavigateBack = router::back,
        onDeleteTimeEntry = viewModel::deleteTimeEntry,
        onGetTimeEntryById = viewModel::getTimeEntryById,
        editTimeEntry = editTimeEntry,
        onGetDate = viewModel::getDate,
        onGetDescription = viewModel::getDescription,
        onGetDuration = viewModel::getDuration,
        onGetEndTime = viewModel::getEndTime,
        onGetProjectId = viewModel::getProjectId,
        onGetStartTime = viewModel::getStartTime,
        projectListState = projectListState.projectListState,
        onGetProjects = viewModel::getProjects
    )
}

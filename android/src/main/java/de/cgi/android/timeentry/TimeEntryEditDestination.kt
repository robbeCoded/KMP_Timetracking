package de.cgi.android.timeentry

import android.text.format.Time
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavBackStackEntry
import de.cgi.android.navigation.Router
import de.cgi.common.data.model.TimeEntry
import io.ktor.http.*
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TimeEntryEditDestination(
    backStackEntry: NavBackStackEntry,
    router: Router,
) {
    val timeEntryId = TimeEntryEditRoute.getTimeEntryId(backStackEntry) ?: ""

    val viewModel = getViewModel<TimeEntryEditViewModel>(parameters = { parametersOf(timeEntryId) })
    // Add any necessary LiveData or StateFlow objects to observe in your composable

    TimeEntryEditScreen(
        onDateChanged = viewModel::dateChanged,
        onStartTimeChanged = viewModel::startTimeChanged,
        onEndTimeChanged = viewModel::endTimeChanged,
        onDurationChanged = viewModel::durationChanged,
        onDescriptionChanged = viewModel::descriptionChanged,
        onProjectChanged = viewModel::projectChanged,
        onSubmitTimeEntry = viewModel::submitTimeEntry,
        onNavigateBack = router::back,
        onDeleteTimeEntry = viewModel::deleteTimeEntry,
    )
}

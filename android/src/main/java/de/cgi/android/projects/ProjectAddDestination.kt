package de.cgi.android.projects

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import de.cgi.android.navigation.Router
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProjectAddDestination(
    router: Router,
) {
    val editProject = false

    val viewModel = getViewModel<ProjectAddViewModel>()

    ProjectAddEditScreen(
        onStartDateChanged = viewModel::startDateChanged,
        onEndDateChanged = viewModel::endDateChanged,
        onNameChanged = viewModel::nameChanged,
        onDescriptionChanged = viewModel::descriptionChanged,

        onSubmitProject = viewModel::submitProject,
        onDeleteProject = { },
        onUpdateProject = { },

        onNavigateBack = { router.back() },
        onGetProjectById = { },
        editProject = editProject,

        onGetStartDate = viewModel::getStartDate,
        onGetEndDate = viewModel::getEndDate,
        onGetName = viewModel::getName,
        onGetDescription = viewModel::getDescription,
    )
}
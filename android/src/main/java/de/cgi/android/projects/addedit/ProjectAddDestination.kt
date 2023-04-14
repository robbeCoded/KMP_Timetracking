package de.cgi.android.projects.addedit

import androidx.compose.runtime.Composable
import de.cgi.android.navigation.Router
import org.koin.androidx.compose.getViewModel

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
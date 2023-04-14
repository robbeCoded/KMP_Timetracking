package de.cgi.android.projects.addedit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import de.cgi.android.navigation.Router
import de.cgi.android.projects.ProjectEditRoute
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ProjectEditDestination(
    backStackEntry: NavBackStackEntry,
    router: Router,
) {
    val projectId = ProjectEditRoute.getProjectById(backStackEntry) ?: "0"
    val editProject = true

    val viewModel = getViewModel<ProjectEditViewModel>(parameters = { parametersOf(projectId) })
    // Add any necessary LiveData or StateFlow objects to observe in your composable

    ProjectAddEditScreen(
        onStartDateChanged = viewModel::startDateChanged,
        onEndDateChanged = viewModel::endDateChanged,
        onNameChanged = viewModel::nameChanged,
        onDescriptionChanged = viewModel::descriptionChanged,

        onSubmitProject = { },
        onDeleteProject = viewModel::deleteProject,
        onUpdateProject = viewModel::updateProject,

        onNavigateBack = { router.back() },
        onGetProjectById = viewModel::getProjectById,
        editProject = editProject,

        onGetStartDate = viewModel::getStartDate,
        onGetEndDate = viewModel::getEndDate,
        onGetName = viewModel::getName,
        onGetDescription = viewModel::getDescription,
    )
}
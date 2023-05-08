package de.cgi.android.projects.addedit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import de.cgi.android.navigation.Router
import de.cgi.android.projects.ProjectEditRoute
import de.cgi.common.repository.ProjectMapProvider
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.O)
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
    val projectMapProvider = get<ProjectMapProvider>()

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

        onColorChanged = viewModel::colorChanged,
        onGetColor = viewModel::getColor,
        onBillableChanged = viewModel::billableChanged,
        onGetBillable = viewModel::getBillable,

        onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() }
    )
}
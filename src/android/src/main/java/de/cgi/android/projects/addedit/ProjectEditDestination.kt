package de.cgi.android.projects.addedit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import de.cgi.android.navigation.Router
import de.cgi.android.projects.ProjectEditRoute
import de.cgi.common.repository.ProjectMapProvider
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ProjectEditDestination(
    backStackEntry: NavBackStackEntry,
    router: Router,
) {
    val di = localDI()
    val projectId = ProjectEditRoute.getProjectById(backStackEntry) ?: "0"
    val editProject = true

    val viewModel: ProjectEditViewModel by di.instance()
    viewModel.projectId = projectId
    val projectMapProvider: ProjectMapProvider by di.instance()

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
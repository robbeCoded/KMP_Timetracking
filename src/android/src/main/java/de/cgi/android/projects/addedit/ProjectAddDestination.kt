package de.cgi.android.projects.addedit

import androidx.compose.runtime.Composable
import de.cgi.android.navigation.Router
import de.cgi.common.projects.ProjectAddViewModel
import de.cgi.common.projects.ProjectListViewModel
import de.cgi.common.repository.ProjectMapProvider
import org.kodein.di.compose.localDI
import org.kodein.di.instance


@Composable
fun ProjectAddDestination(
    router: Router,
) {
    val editProject = false
    val di = localDI()
    val viewModel: ProjectAddViewModel by di.instance()
    val listViewModel: ProjectListViewModel by di.instance()

    val projectMapProvider: ProjectMapProvider by di.instance()

    ProjectAddEditScreen(
        onStartDateChanged = viewModel::startDateChanged,
        onEndDateChanged = viewModel::endDateChanged,
        onNameChanged = viewModel::nameChanged,
        onDescriptionChanged = viewModel::descriptionChanged,

        onSubmitProject = viewModel::submitProject,
        onDeleteProject = { },
        onUpdateProject = { },

        onNavigateBack = { router.backFromAddEdit(listViewModel::notifyProjectUpdates) },
        onGetProjectById = { },
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
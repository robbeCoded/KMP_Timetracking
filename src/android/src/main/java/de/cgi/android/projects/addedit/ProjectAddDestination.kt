package de.cgi.android.projects.addedit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import de.cgi.android.navigation.Router
import de.cgi.android.projects.list.ProjectListViewModel
import de.cgi.common.repository.ProjectMapProvider
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProjectAddDestination(
    router: Router,
) {
    val editProject = false

    val viewModel = getViewModel<ProjectAddViewModel>()
    val listViewModel = getViewModel<ProjectListViewModel>()

    val projectMapProvider = get<ProjectMapProvider>()

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
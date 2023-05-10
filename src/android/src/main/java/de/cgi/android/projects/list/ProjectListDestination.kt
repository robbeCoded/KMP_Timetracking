package de.cgi.android.projects.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import de.cgi.common.projects.ProjectListViewModel
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ProjectListDestination(
    router: Router,
) {
    val di = localDI()
    val viewModel: ProjectListViewModel by di.instance()
    val projectListState by viewModel.listState.collectAsState()

    LaunchedEffect(viewModel.updateTrigger.value) {
        viewModel.getProjects()
    }

    ProjectListScreen(
        projectListState = projectListState.projectListState,
        onNewProjectClick = { router.showProjectAdd() },
        onProjectClick = { router.showProjectEdit(it) },
        onDeleteProject = viewModel::deleteProject,
        removeProjectState = projectListState.removeProjectState,
        reloadProjects = viewModel::getProjects
    )
}
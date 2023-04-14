package de.cgi.android.projects.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ProjectListDestination(
    router: Router,
) {
    val viewModel = getViewModel<ProjectListViewModel>()
    val projectListState by viewModel.listState.collectAsState()

    ProjectListScreen(
        projectListState = projectListState.projectListState,
        onNewProjectClick = { router.showProjectAdd() },
        onProjectClick = { router.showProjectEdit(it) },
        onDeleteProject = viewModel::deleteProject,
        removeProjectState = projectListState.removeProjectState,
        reloadProjects = viewModel::getProjects
    )
}
package de.cgi.android.projects.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ProjectListDestination(
    router: Router,
) {
    val viewModel = getViewModel<ProjectListViewModel>()
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
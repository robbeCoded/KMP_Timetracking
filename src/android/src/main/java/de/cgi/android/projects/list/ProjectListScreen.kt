package de.cgi.android.projects.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import de.cgi.android.util.AsyncData
import de.cgi.android.util.GenericError
import de.cgi.common.data.model.Project
import de.cgi.common.util.ResultState


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ProjectListScreen(
    projectListState: ResultState<List<Project>>,
    removeProjectState: ResultState<Unit>?,
    onNewProjectClick: () -> Unit,
    onProjectClick: (Project) -> Unit,
    onDeleteProject: (Project) -> Unit,
    reloadProjects: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = onNewProjectClick) {
                Icon(imageVector = FeatherIcons.Plus, contentDescription = "Add project")
            }
        }) {
        Box(Modifier.padding(it)) {
            AsyncData(resultState = projectListState, errorContent = {
                GenericError(
                    onDismissAction = reloadProjects
                )
            }) { projectList ->
                projectList?.let {
                    LazyColumn {
                        items(projectList, key = { project -> project.id }) { item ->
                            ProjectListItem(
                                onClick = onProjectClick,
                                project = item,
                                onDeleteProject = onDeleteProject
                            )
                        }
                    }
                }


            }
        }
    }

}

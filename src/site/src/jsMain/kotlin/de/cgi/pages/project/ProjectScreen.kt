package de.cgi.pages.project

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.projects.ProjectAddViewModel
import de.cgi.common.projects.ProjectEditViewModel
import de.cgi.common.projects.ProjectListViewModel
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.components.layouts.PageLayout
import de.cgi.components.styles.PageHeaderStyle
import de.cgi.components.styles.PageTitle
import de.cgi.components.styles.Theme
import de.cgi.components.styles.VerticalSpacer
import de.cgi.components.util.AsyncData
import de.cgi.components.widgets.ProjectAddForm
import de.cgi.components.widgets.ProjectEditForm
import de.cgi.components.widgets.ProjectListItem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page(routeOverride = "list")
@Composable
fun ProjectsListScreen() {
    val di = localDI()
    val projectListViewModel: ProjectListViewModel by di.instance()
    val projectEditViewModel: ProjectEditViewModel by di.instance()
    val projectAddViewModel: ProjectAddViewModel by di.instance()
    val projectMapProvider: ProjectMapProvider by di.instance()
    val ctx = rememberPageContext()

    val showEdit = remember { mutableStateOf(false) }

    val projectListState by projectListViewModel.listState.collectAsState()

    LaunchedEffect(projectListViewModel.updateTrigger.value) {
        projectListViewModel.getProjects()
    }

    PageLayout(title = "Project", pageContext = ctx) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {
            Box(
                PageHeaderStyle.toModifier()
            ) {
                P(PageTitle.toModifier().toAttrs()) {
                    Text("Project")
                }
            }
            VerticalSpacer(8)
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.px)
                        .width(55.percent),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    AsyncData(resultState = projectListState.projectListState) { projectList ->
                        projectList?.let {
                            if (projectList.isNotEmpty()) {
                                projectList.forEach { project ->
                                    ProjectListItem(
                                        project = project,
                                        onClick = { selectedProject ->
                                            projectEditViewModel.clear()
                                            projectEditViewModel.projectId = selectedProject.id
                                            projectEditViewModel.getProjectById()
                                            showEdit.value = true
                                        }
                                    )
                                }
                            } else {
                                Text("There are no Projects yet.")
                            }
                        }
                    }
                }

                //Divider
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(5.percent),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .height(90.percent)
                            .width(1.px)
                            .backgroundColor(Theme.Black.rgb)
                    )
                }

                if (showEdit.value) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(40.percent),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProjectEditForm(
                            viewModel = projectEditViewModel,
                            onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() },
                            onUpdate = { projectListViewModel.notifyProjectUpdates() },
                            updateDeleteClicked = { showEdit.value = false }
                        )
                        Button(
                            modifier = Modifier
                                .margin(8.px)
                                .backgroundColor(Theme.ItemColor.rgb),
                            onClick = {
                                showEdit.value = false
                            }) {
                            Text("New Project")
                        }
                    }

                } else {
                    ProjectAddForm(
                        viewModel = projectAddViewModel,
                        onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() },
                        onUpdate = { projectListViewModel.notifyProjectUpdates() }
                    )
                }

            }


        }
    }

}

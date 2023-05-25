package de.cgi.pages.project

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.data.model.Project
import de.cgi.common.projects.ProjectAddViewModel
import de.cgi.common.projects.ProjectEditViewModel
import de.cgi.common.projects.ProjectListViewModel
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.util.format
import de.cgi.components.layouts.PageLayout
import de.cgi.components.styles.PageHeaderStyle
import de.cgi.components.styles.PageTitle
import de.cgi.components.styles.Theme
import de.cgi.components.styles.VerticalSpacer
import de.cgi.components.util.AsyncData
import de.cgi.components.widgets.ProjectAddForm
import de.cgi.components.widgets.ProjectEditForm
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
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
                    Text("Projects")
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

                if (showEdit.value) {
                    ProjectEditForm(
                        viewModel = projectEditViewModel,
                        onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() },
                        onUpdate = { projectListViewModel.notifyProjectUpdates() },
                        updateDeleteClicked = { showEdit.value = false }
                    )
                } else {
                    ProjectAddForm(
                        viewModel = projectAddViewModel,
                        onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() },
                        onUpdate = { projectListViewModel.notifyProjectUpdates() }
                    )
                }

            }


            Button(onClick = {
                showEdit.value = false
            }) {
                Text("Add project")
            }

        }
    }

}

@Composable
fun ProjectListItem(
    project: Project,
    onClick: (Project) -> Unit,
) {
    val startDate: LocalDate = project.startDate.toLocalDate()
    val endDate: LocalDate = project.endDate.toLocalDate()
    val color = project.color.toString()


    Box(
        modifier = Modifier
            .margin(8.px)
            .padding(8.px)
            .borderRadius(10.px)
            .fillMaxWidth()
            .styleModifier { property("background-color", color) }
            .onClick { onClick(project) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Div(
                Modifier
                    .fontWeight(FontWeight.SemiBold)
                    .toAttrs()
            ) {
                Text(project.name)
            }
            Text(project.description ?: "Keine Beschreibung")
            Spacer()
            Text(
                "${startDate.format()} - ${endDate.format()}"
            )

        }
    }
}
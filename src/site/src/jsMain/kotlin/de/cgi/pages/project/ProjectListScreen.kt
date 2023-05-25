package de.cgi.pages.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.data.model.Project
import de.cgi.common.projects.ProjectListViewModel
import de.cgi.common.util.format
import de.cgi.components.layouts.PageLayout
import de.cgi.components.styles.PageHeaderStyle
import de.cgi.components.styles.PageTitle
import de.cgi.components.styles.Theme
import de.cgi.components.styles.VerticalSpacer
import de.cgi.components.util.AsyncData
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
    val viewModel: ProjectListViewModel by di.instance()
    val ctx = rememberPageContext()

    val projectListState by viewModel.listState.collectAsState()

    LaunchedEffect(viewModel.updateTrigger.value) {
        viewModel.getProjects()
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
                AsyncData(resultState = projectListState.projectListState) { projectList ->
                    projectList?.let {
                        if (projectList.isNotEmpty()) {
                            projectList.forEach { project ->
                                ProjectListItem(
                                    project = project,
                                    onClick = { ctx.router.navigateTo("/edit/${project.id}") }
                                )
                            }
                        } else {
                            Text("Du hast noch keine Projekte angelegt.")
                        }
                    }
                }

            }


            Button(onClick = { ctx.router.navigateTo("/project/add") }) {
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


    Box(
        modifier = Modifier
            .margin(8.px)
            .padding(8.px)
            .borderRadius(10.px)
            .width(55.percent)
            .backgroundColor(Theme.ItemColor.rgb)
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
            ){
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
package de.cgi.pages.project


import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import de.cgi.common.projects.ProjectEditViewModel
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.components.layouts.PageLayout
import de.cgi.components.styles.*
import de.cgi.components.widgets.ProjectEditForm
import kotlinx.datetime.*
import org.kodein.di.compose.localDI
import org.kodein.di.instance

/*
@Page("/edit/{id}")
@Composable
fun ProjectEditScreen() {
    val di = localDI()
    val ctx = rememberPageContext()

    val projectId = ctx.route.params.getValue("id")

    val projectEditViewModel: ProjectEditViewModel by di.instance()
    projectEditViewModel.projectId = projectId
    val projectMapProvider: ProjectMapProvider by di.instance()

    PageLayout(title = "Project", pageContext = ctx) {
        ProjectEditForm(
            viewModel = projectEditViewModel,
            onNavigateBack = { ctx.router.navigateTo("/project/list") },
            onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() }
        )
    }
}
*/
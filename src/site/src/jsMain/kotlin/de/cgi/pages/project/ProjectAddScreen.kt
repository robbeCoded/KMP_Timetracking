package de.cgi.pages.project

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import de.cgi.common.projects.ProjectAddViewModel
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.components.layouts.PageLayout
import de.cgi.components.widgets.ProjectAddForm
import org.kodein.di.compose.localDI
import org.kodein.di.instance

/*
@Page(routeOverride = "add")
@Composable
fun ProjectAddScreen() {
    val di = localDI()
    val projectAddViewModel: ProjectAddViewModel by di.instance()
    val ctx = rememberPageContext()
    val projectMapProvider: ProjectMapProvider by di.instance()
    PageLayout(title = "Project", pageContext = ctx) {
        ProjectAddForm(
            viewModel = projectAddViewModel,
            onNavigateBack = { ctx.router.navigateTo("/project/list") },
            onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() }
        )
    }
}

*/
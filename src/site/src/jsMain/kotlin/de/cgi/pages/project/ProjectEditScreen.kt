package de.cgi.pages.project


import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toAttrs
import de.cgi.common.projects.ProjectEditViewModel
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.components.layouts.PageLayout
import de.cgi.components.styles.*
import de.cgi.components.widgets.InputFieldStyleBig
import de.cgi.components.widgets.InputFieldStyleSmall
import de.cgi.components.widgets.ProjectEditForm
import kotlinx.datetime.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput
import org.kodein.di.compose.localDI
import org.kodein.di.instance


@Page("/edit/{id}")
@Composable
fun ProjectEditScreen() {
    val di = localDI()
    val ctx = rememberPageContext()

    val projectId = ctx.route.params.getValue("id")

    val viewModel: ProjectEditViewModel by di.instance()
    viewModel.projectId = projectId
    val projectMapProvider: ProjectMapProvider by di.instance()

    PageLayout(title = "Project", pageContext = ctx) {
        ProjectEditForm(
            viewModel = viewModel,
            onNavigateBack = { ctx.router.navigateTo("/project/list") },
            onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() }
        )
    }
}

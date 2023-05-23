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
import kotlinx.datetime.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
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

    PageLayout(title = "Neues Projekt") {
        ProjectEditForm(
            viewModel = viewModel,
            onNavigateBack = { ctx.router.navigateTo("/project/list") },
            onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() }
        )
    }
}

@Composable
fun ProjectEditForm(
    viewModel: ProjectEditViewModel,
    onNavigateBack: () -> Unit,
    onProjectsUpdated: () -> Unit
) {

    val startDate = viewModel.startDate.collectAsState()
    val endDate = viewModel.endDate.collectAsState()
    val nameFlow = viewModel.name.collectAsState()
    val descriptionFlow = viewModel.description.collectAsState()
    val selectedColor = viewModel.color.collectAsState()
    val billable = viewModel.billable.collectAsState()


    var description by remember { mutableStateOf(descriptionFlow.value ?: "") }
    var name by remember { mutableStateOf(nameFlow.value) }

    LaunchedEffect(key1 = "edit") {
        viewModel.getProjectById()
    }

    Column(modifier = Modifier.fillMaxHeight().width(450.px)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Label {
                    Text("Start Date")
                }
                Input(
                    InputType.Date,
                    attrs = listOf(InputFieldStyleSmall)
                        .toAttrs {
                            value(startDate.value.toString())
                            onChange {
                                viewModel.startDateChanged(it.value.toLocalDate())
                            }
                        }
                )
            }
            Column {
                Label {
                    Text("End Date")
                }
                Input(
                    InputType.Date,
                    attrs = listOf(InputFieldStyleSmall)
                        .toAttrs {
                            value(endDate.value.toString())
                            onChange {
                                viewModel.endDateChanged(it.value.toLocalDate())
                            }
                        }
                )
            }


        }
        Label {
            Text("Project Title")
        }
        Input(
            InputType.Text,
            attrs = listOf(InputFieldStyleBig)
                .toAttrs {
                    value(name)
                    onChange {
                        name = it.value
                    }
                }
        )
        Label {
            Text("Description")
        }
        Input(
            InputType.Text,
            attrs = listOf(InputFieldStyleBig)
                .toAttrs {
                    value(description)
                    onChange {
                        description = it.value
                    }
                }
        )
        VerticalSpacer(8)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
            Input(
                InputType.Checkbox,
                attrs = {
                    checked(billable.value)
                    onChange {
                        viewModel.billableChanged(it.value)
                    }
                }
            )
            HorizontalSpacer(16)
            Text("Project is Billable")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomColors.projectColorsList.forEach { color ->
                val colorString = color.toString()
                Box(
                    modifier = Modifier
                        .height(50.px)
                        .width(50.px)
                        .border(
                            width = if (selectedColor.value == colorString) 3.px else 1.px,
                            color = if (selectedColor.value == colorString) CustomColors.actionPrimary else CustomColors.black,
                        )
                        .background(color.toString())
                        .onClick {
                            viewModel.colorChanged(colorString)
                        }
                )
            }
        }
        Button(
            modifier = Modifier.width(450.px),
            onClick = {
                viewModel.descriptionChanged(description)
                viewModel.nameChanged(name)
                viewModel.updateProject()
                onProjectsUpdated()
                onNavigateBack()
            }) {
            Text("Update")
        }
        Button(
            modifier = Modifier.width(450.px),
            onClick = {
                viewModel.deleteProject()
                onProjectsUpdated()
                onNavigateBack()
            }) {
            Text("Delete")
        }

    }
}
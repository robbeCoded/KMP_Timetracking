package de.cgi.pages.timeentry

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toAttrs
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryAddViewModel
import de.cgi.common.timeentry.TimeEntryListViewModel
import de.cgi.common.util.ResultState
import de.cgi.components.layouts.PageLayout
import de.cgi.components.widgets.InputFieldStyle
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page(routeOverride = "add")
@Composable
fun TimeEntryAddScreen() {
    val di = localDI()
    val viewModel: TimeEntryAddViewModel by di.instance()
    val listViewModel: TimeEntryListViewModel by di.instance()
    val projectMapProvider: ProjectMapProvider by di.instance()
    val ctx = rememberPageContext()

    PageLayout(title = "Time Tracking", pageContext = ctx) {
        TimeEntryAdd(
            viewModel = viewModel,
            onNavigateBack = { ctx.router.navigateTo("/timeentry/list") },
            onTimeEntriesUpdated = listViewModel::notifyTimeEntryUpdates,
            projectMapProvider = projectMapProvider,
        )
    }

}

@Composable
fun TimeEntryAdd(
    viewModel: TimeEntryAddViewModel,
    onNavigateBack: () -> Unit,
    onTimeEntriesUpdated: () -> Unit,
    projectMapProvider: ProjectMapProvider,
) {
    val projectListState = projectMapProvider.getProjectNameMapValue()

    val date by viewModel.date.collectAsState()
    val startTime = viewModel.startTime.collectAsState()
    val endTime = viewModel.endTime.collectAsState()

    val duration = viewModel.duration.collectAsState()

    val project = viewModel.projectName.collectAsState()
    val selectedProject = viewModel.projectId.collectAsState()

    val description = remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.width(450.px), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Label {
                    Text("Date")
                }
                Input(
                    InputType.Date,
                    attrs = listOf(InputFieldStyle)
                        .toAttrs {
                            name("date")
                            value(date.toString())
                            onChange {
                                viewModel.dateChanged(it.value.toLocalDate())
                            }
                        }
                )
            }
            Column {
                Label {
                    Text("Duration")
                }
                Input(
                    InputType.Time,
                    attrs = listOf(InputFieldStyle)
                        .toAttrs {
                            name("duration")
                            value(duration.value.toString())
                            onChange {
                                viewModel.durationChanged(it.value.toLocalTime())
                            }
                        }
                )
            }
        }


        Row(modifier = Modifier.width(450.px), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Label {
                    Text("Start Time")
                }
                Input(
                    InputType.Time,
                    attrs = listOf(InputFieldStyle)
                        .toAttrs {
                            name("starttime")
                            value(startTime.value.toString())
                            onChange {
                                viewModel.startTimeChanged(it.value.toLocalTime())
                            }
                        }
                )

            }
            Column {
                Label {
                    Text("End Time")
                }
                Input(
                    InputType.Time,
                    attrs = listOf(InputFieldStyle)
                        .toAttrs {
                            name("endtime")
                            value(endTime.value.toString())
                            onChange {
                                viewModel.endTimeChanged(it.value.toLocalTime())
                            }
                        }
                )
            }
        }
        Column(modifier = Modifier.width(450.px), horizontalAlignment = Alignment.Start) {
            Label {
                Text("Description")
            }
            TextInput(
                value = description.value,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        onInput {
                            description.value = it.value
                        }
                    }
            )
            Label {
                Text("Project")
            }
            Select(attrs = {
                onChange {
                    val projectName = projectMapProvider.getProjectNameById(it.value)
                    viewModel.projectChanged(it.value, projectName)
                }
            }) {
                when (projectListState) {
                    is ResultState.Success -> {
                        val projectList = projectListState.data
                        Option(
                            value = "",
                            attrs = {
                                if (selectedProject.value == "" || selectedProject.value == null) {
                                    selected()
                                }
                            }) {
                            Text("Internal")
                        }
                        projectList?.forEach { project ->
                            Option(
                                value = project.key,
                                attrs = {
                                    if (project.key == selectedProject.value) {
                                        selected()
                                    }
                                }) {
                                Text(project.value)
                            }
                        }
                    }
                    is ResultState.Error -> {
                        Option(value = "") {
                            Text("Error loading projects")
                        }
                    }
                    is ResultState.Loading -> {
                        Option(value = "") {
                            Text("Loading projects...")
                        }
                    }
                }
            }
            Button(
                modifier = Modifier.width(450.px),
                onClick = {
                    viewModel.descriptionChanged(description.value)
                    viewModel.submitTimeEntry()
                    onTimeEntriesUpdated()
                    onNavigateBack()
                }) {
                Text("Submit")
            }

        }
    }
}


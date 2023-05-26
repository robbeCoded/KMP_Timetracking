package de.cgi.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryAddViewModel
import de.cgi.common.util.ResultState
import de.cgi.components.styles.Heading3
import de.cgi.components.styles.VerticalSpacer
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*


@Composable
fun TimeEntryAddForm(
    viewModel: TimeEntryAddViewModel,
    onTimeEntriesUpdated: () -> Unit,
    projectMapProvider: ProjectMapProvider,
) {
    val projectListState = projectMapProvider.getProjectNameMapValue()

    val date by viewModel.date.collectAsState()
    val startTime = viewModel.startTime.collectAsState()
    val endTime = viewModel.endTime.collectAsState()
    val duration = viewModel.duration.collectAsState()

    val selectedProject = viewModel.projectId.collectAsState()

    val description = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(8.px)
            .fillMaxHeight()
            .width(40.percent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Div(
                Heading3.toAttrs()
            ) {
                Text("New Time Entry")
            }
            VerticalSpacer(16)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.width(40.percent),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Div(de.cgi.components.styles.Label.toAttrs()) {
                        Text("Date")
                    }
                    Input(
                        InputType.Date,
                        attrs = listOf(InputFieldStyle)
                            .toAttrs {
                                value(date.toString())
                                onChange {
                                    viewModel.dateChanged(it.value.toLocalDate())
                                }
                            }
                    )
                }
                Column(
                    modifier = Modifier.width(40.percent),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Div(de.cgi.components.styles.Label.toAttrs()) {
                        Text("Duration")
                    }
                    Input(
                        InputType.Time,
                        attrs = listOf(InputFieldStyle)
                            .toAttrs {
                                value(duration.value.toString())
                                onChange {
                                    viewModel.durationChanged(it.value.toLocalTime())
                                }
                            }
                    )
                }

            }

            VerticalSpacer(16)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.width(40.percent),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Div(de.cgi.components.styles.Label.toAttrs()) {
                        Text("Start Time")
                    }
                    Input(
                        InputType.Time,
                        attrs = listOf(InputFieldStyle)
                            .toAttrs {
                                value(startTime.value.toString())
                                onChange {
                                    viewModel.startTimeChanged(it.value.toLocalTime())
                                }
                            }
                    )
                }
                Column(
                    modifier = Modifier.width(40.percent),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Div(de.cgi.components.styles.Label.toAttrs()) {
                        Text("End Time")
                    }
                    Input(
                        InputType.Time,
                        attrs = listOf(InputFieldStyle)
                            .toAttrs {
                                value(endTime.value.toString())
                                onChange {
                                    viewModel.endTimeChanged(it.value.toLocalTime())
                                }
                            }
                    )
                }
            }

            VerticalSpacer(16)

            Div(de.cgi.components.styles.Label.toAttrs()) {
                Text("Description")
            }
            TextArea(
                value = description.value,
                attrs = listOf(InputFieldStyleBig)
                    .toAttrs {
                        onInput {
                            description.value = it.value
                        }
                    }
            )
            VerticalSpacer(16)

            Div(de.cgi.components.styles.Label.toAttrs()) {
                Text("Project")
            }
            Select(
                InputFieldStyle.toAttrs {
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

            VerticalSpacer(32)

            AddEditFormButton(
                onClick = {
                    viewModel.descriptionChanged(description.value)
                    viewModel.submitTimeEntry()
                    onTimeEntriesUpdated()
                },
                text = "Submit"
            )

        }

    }


}


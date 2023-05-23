package de.cgi.pages.timeentry


import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toAttrs
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryEditViewModel
import de.cgi.components.layouts.PageLayout
import de.cgi.components.util.JsJodaTimeZoneModule
import de.cgi.components.widgets.InputFieldStyleBig
import de.cgi.components.widgets.InputFieldStyleSmall
import kotlinx.datetime.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.compose.localDI
import org.kodein.di.instance


@Page("edit/{id}")
@Composable
fun TimeEntryEditScreen() {
    val di = localDI()
    val ctx = rememberPageContext()

    val timeEntryId = ctx.route.params.getValue("id")

    val viewModel: TimeEntryEditViewModel by di.instance()
    viewModel.timeEntryId = timeEntryId

    val projectMapProvider: ProjectMapProvider by di.instance()
    val jsJodaTz = JsJodaTimeZoneModule
    PageLayout(title = "Edit Time Entry") {
        Column(modifier = Modifier.fillMaxSize()) {
            TimeEntryEditForm(
                viewModel = viewModel,
                onNavigateBack = { ctx.router.navigateTo("/timeentry/list") },
            )
        }

    }
}

@Composable
fun TimeEntryEditForm(
    viewModel: TimeEntryEditViewModel,
    onNavigateBack: () -> Unit,
) {
    val date by viewModel.date.collectAsState()
    val startTime = viewModel.startTime.collectAsState()
    val endTime = viewModel.endTime.collectAsState()

    val duration = viewModel.duration.collectAsState()

    val project = viewModel.projectName.collectAsState()
    val selectedProject = viewModel.projectId.collectAsState()
    val description = viewModel.description.collectAsState()

    LaunchedEffect(key1 = "edit") {
        viewModel.getTimeEntryById()
    }

    console.log(date)
    console.log(startTime.value)
    console.log(endTime.value)

    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.width(450.px), horizontalArrangement = Arrangement.SpaceBetween) {
            Column() {
                Label {
                    Text("Date")
                }
                Input(
                    InputType.Date,
                    attrs = listOf(InputFieldStyleSmall)
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
                    attrs = listOf(InputFieldStyleSmall)
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
            Column() {
                Label {
                    Text("Start Time")
                }
                Input(
                    InputType.Time,
                    attrs = listOf(InputFieldStyleSmall)
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
                    attrs = listOf(InputFieldStyleSmall)
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
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyleBig)
                    .toAttrs {
                        name("description")
                        value(description.value.toString())
                        onChange {
                            viewModel.descriptionChanged(it.value)
                        }
                    }
            )
            Label {
                Text("Project")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyleBig)
                    .toAttrs {
                        name("project")
                        value(project.value.toString())
                        onChange {
                            //onProjectChanged(it.value)
                        }
                    }
            )
            Button(
                modifier = Modifier.width(450.px),
                onClick = {
                viewModel.updateTimeEntry()
                viewModel.clear()
                onNavigateBack()
            }) {
                Text("Update")
            }
            Button(
                modifier = Modifier.width(450.px),
                onClick = {
                viewModel.deleteTimeEntry()
                onNavigateBack()
                viewModel.clear()
            }) {
                Text("Delete")
            }
        }

    }
}
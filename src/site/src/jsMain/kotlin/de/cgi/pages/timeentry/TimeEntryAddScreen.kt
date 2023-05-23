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
import de.cgi.common.timeentry.TimeEntryAddViewModel
import de.cgi.common.timeentry.TimeEntryListViewModel
import de.cgi.components.layouts.PageLayout
import de.cgi.components.widgets.InputFieldStyleBig
import de.cgi.components.widgets.InputFieldStyleSmall
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page(routeOverride = "add")
@Composable
fun TimeEntryAddScreen() {
    val di = localDI()
    val viewModel: TimeEntryAddViewModel by di.instance()
    val listViewModel: TimeEntryListViewModel by di.instance()

    val ctx = rememberPageContext()

    PageLayout(title = "Add Time Entry") {
        TimeEntryAdd(
            viewModel = viewModel,
            onNavigateBack = { ctx.router.navigateTo("/timeentry/list") },
            onTimeEntriesUpdated = listViewModel::notifyTimeEntryUpdates
        )
    }

}

@Composable
fun TimeEntryAdd(
    viewModel: TimeEntryAddViewModel,
    onNavigateBack: () -> Unit,
    onTimeEntriesUpdated: () -> Unit,
) {
    val date by viewModel.date.collectAsState()
    val startTime = viewModel.startTime.collectAsState()
    val endTime = viewModel.endTime.collectAsState()

    val duration = viewModel.duration.collectAsState()

    val project = viewModel.projectName.collectAsState()
    val selectedProject = viewModel.projectId.collectAsState()
    val descriptionStateFlow = viewModel.description.collectAsState()

    var description by remember { mutableStateOf(descriptionStateFlow.value ?: "") }



    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.width(450.px), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
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
            Column {
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
                        value(description)
                        onChange {
                            description = it.value
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
                    viewModel.descriptionChanged(description)
                    viewModel.submitTimeEntry()
                    onTimeEntriesUpdated()
                    onNavigateBack()
                }) {
                Text("Submit")
            }

        }
    }
}


package de.cgi.pages.timeentry


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryEditViewModel
import de.cgi.components.layouts.PageLayout
import de.cgi.components.styles.MainButtonStyle
import de.cgi.components.styles.Theme
import de.cgi.components.util.JsJodaTimeZoneModule
import de.cgi.components.widgets.AuthContainerStyle
import de.cgi.components.widgets.InputFieldStyle
import kotlinx.datetime.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
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
        TimeEntryEditForm(
            onUpdateClick = viewModel::updateTimeEntry,
            onDeleteClick = viewModel::deleteTimeEntry,
            onDateChanged = viewModel::dateChanged,
            onStartTimeChanged = viewModel::startTimeChanged,
            onEndTimeChanged = viewModel::endTimeChanged,
            onDurationChanged = viewModel::durationChanged,
            onDescriptionChanged = viewModel::descriptionChanged,
            onProjectChanged = viewModel::projectChanged,
            onGetDate = viewModel::getDate,
            onGetDescription = viewModel::getDescription,
            onGetDuration = viewModel::getDuration,
            onGetEndTime = viewModel::getEndTime,
            onGetProjectId = viewModel::getProjectId,
            onGetProjectName = viewModel::getProjectName,
            onGetStartTime = viewModel::getStartTime,
            onGetTimeEntryById = viewModel::getTimeEntryById,
        )
    }
}
@Composable
fun TimeEntryEditForm(
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onDurationChanged: (LocalTime) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onProjectChanged: (String, String) -> Unit,
    onGetStartTime: () -> LocalTime?,
    onGetEndTime: () -> LocalTime?,
    onGetDuration: () -> LocalTime?,
    onGetDate: () -> LocalDate?,
    onGetDescription: () -> String?,
    onGetProjectId: () -> String?,
    onGetProjectName: () -> String?,
    onGetTimeEntryById: () -> Unit,
) {
    LaunchedEffect(key1 = "edit") {
        onGetTimeEntryById()
    }

    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    val date = remember { mutableStateOf<LocalDate?>(null) }
    val startTime =
        remember { mutableStateOf<LocalTime?>(null) }
    val endTime =
        remember { mutableStateOf<LocalTime?>(null) }
    val duration = remember { mutableStateOf<LocalTime?>(null) }
    val project = remember { mutableStateOf("") }
    val selectedProject = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    date.value = onGetDate() ?: currentDateTime.date
    startTime.value = onGetStartTime() ?: currentDateTime.time
    endTime.value = onGetEndTime() ?: currentDateTime.time
    duration.value = onGetDuration() ?: LocalTime(0, 0)
    project.value = onGetProjectId() ?: ""
    description.value = onGetDescription() ?: ""
    selectedProject.value = onGetProjectName() ?: ""

    Form(attrs = listOf(AuthContainerStyle).toAttrs {
        onSubmit { evt ->
            evt.preventDefault()
            onUpdateClick()
        }
    }) {
        Column(Modifier.fillMaxSize()) {
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Date")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("date")
                        value(date.value.toString())
                        onChange {
                            date.value = it.value.toLocalDate()
                            onDateChanged(it.value.toLocalDate())
                        }
                    }
            )
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Duration")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("duration")
                        value(duration.value.toString())
                        onChange {
                            duration.value = it.value.toLocalTime()
                            onDurationChanged(it.value.toLocalTime())
                        }
                    }
            )

            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Start Time")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("starttime")
                        value(startTime.value.toString())
                        onChange {
                            startTime.value = it.value.toLocalTime()
                            onStartTimeChanged(it.value.toLocalTime())
                        }
                    }
            )
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("End Time")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("endtime")
                        value(endTime.value.toString())
                        onChange {
                            endTime.value = it.value.toLocalTime()
                            onEndTimeChanged(it.value.toLocalTime())
                        }
                    }
            )
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Description")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("description")
                        value(description.value)
                        onChange {
                            description.value = it.value
                            onDescriptionChanged(it.value)
                        }
                    }
            )
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Project")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("project")
                        onChange {
                            project.value = it.value
                            //onProjectChanged(it.value)
                        }
                    }
            )
            Button(
                attrs = MainButtonStyle.toModifier()
                    .height(40.px)
                    .border(width = 0.px)
                    .borderRadius(r = 5.px)
                    .backgroundColor(Theme.Primary.rgb)
                    .color(Colors.White)
                    .cursor(Cursor.Pointer)
                    .toAttrs()
            ) {
                Text("Update")
            }
        }
    }
}
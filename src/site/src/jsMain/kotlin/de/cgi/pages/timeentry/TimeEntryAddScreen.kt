package de.cgi.pages.timeentry

import androidx.compose.runtime.*
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
import de.cgi.common.timeentry.TimeEntryAddViewModel
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

@Page(routeOverride = "add")
@Composable
fun TimeEntryAddScreen() {
    val di = localDI()
    val viewModel: TimeEntryAddViewModel by di.instance()
    val ctx = rememberPageContext()

    val jsJodaTz = JsJodaTimeZoneModule

    TimeEntryAddForm(
        onSubmitClick = viewModel::submitTimeEntry,
        onDeleteClick = {},
        onDateChanged = viewModel::dateChanged,
        onStartTimeChanged = viewModel::startTimeChanged,
        onEndTimeChanged = viewModel::endTimeChanged,
        onDurationChanged = viewModel::durationChanged,
        onDescriptionChanged = viewModel::descriptionChanged,
        onProjectChanged = viewModel::projectChanged,
        editTimeEntry = false,
        onGetDate = viewModel::getDate,
        onGetDescription = viewModel::getDescription,
        onGetDuration = viewModel::getDuration,
        onGetEndTime = viewModel::getEndTime,
        onGetProjectId = viewModel::getProjectId,
        onGetProjectName = viewModel::getProjectName,
        onGetStartTime = viewModel::getStartTime,
    )
}

@Composable
fun TimeEntryAddForm(
    onSubmitClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onDurationChanged: (LocalTime) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onProjectChanged: (String, String) -> Unit,
    editTimeEntry: Boolean,
    onGetStartTime: () -> LocalTime?,
    onGetEndTime: () -> LocalTime?,
    onGetDuration: () -> LocalTime?,
    onGetDate: () -> LocalDate?,
    onGetDescription: () -> String?,
    onGetProjectId: () -> String?,
    onGetProjectName: () -> String?,
) {
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
            onSubmitClick()
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
            DatePicker(date.value.toString()) { newDate ->
                date.value = newDate.toLocalDate()
                onDateChanged(newDate.toLocalDate())
            }
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
                Text("Submit")
            }
        }

    }
}

@JsModule("flatpickr")
@JsNonModule
external val flatpickr: dynamic


var idForPicker = 0

@Composable
fun DatePicker(value: String, onValueChange: (String) -> Unit) {
    val id = remember { "input-${idForPicker++}" }


    Div {
        Input(
            type = InputType.Text,
            attrs = listOf(InputFieldStyle)
                .toAttrs {
                    value(value)
                    onChange {
                        onValueChange(it.value.toString())
                    }
                }
        )
    }

    LaunchedEffect(Unit) {
        flatpickr(id, flatpickr) as Unit
    }
}




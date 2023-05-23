package de.cgi.pages.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.projects.ProjectAddViewModel
import de.cgi.common.repository.ProjectMapProvider
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


@Page(routeOverride = "add")
@Composable
fun ProjectAddScreen() {
    val di = localDI()
    val viewModel: ProjectAddViewModel by di.instance()
    val ctx = rememberPageContext()
    val editProject = false
    val projectMapProvider: ProjectMapProvider by di.instance()
    val jsJodaTz = JsJodaTimeZoneModule
    PageLayout(title = "Neues Projekt") {
        ProjectAddEditForm(
            onStartDateChanged = viewModel::startDateChanged,
            onEndDateChanged = viewModel::endDateChanged,
            onNameChanged = viewModel::nameChanged,
            onDescriptionChanged = viewModel::descriptionChanged,

            onSubmitProject = viewModel::submitProject,
            onDeleteProject = { },
            onUpdateProject = { },

            onNavigateBack = { ctx.router.navigateTo("/project/list") },
            onGetProjectById = { },
            editProject = editProject,

            onGetStartDate = viewModel::getStartDate,
            onGetEndDate = viewModel::getEndDate,
            onGetName = viewModel::getName,
            onGetDescription = viewModel::getDescription,

            onColorChanged = viewModel::colorChanged,
            onGetColor = viewModel::getColor,
            onBillableChanged = viewModel::billableChanged,
            onGetBillable = viewModel::getBillable,

            onProjectsUpdated = { projectMapProvider.notifyProjectUpdates() }
        )
    }
}

@Composable
fun ProjectAddEditForm(
    onStartDateChanged: (LocalDate) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,

    onSubmitProject: () -> Unit,
    onDeleteProject: () -> Unit,
    onUpdateProject: () -> Unit,

    onNavigateBack: () -> Unit,
    onGetProjectById: () -> Unit,
    editProject: Boolean,

    onGetStartDate: () -> LocalDate?,
    onGetEndDate: () -> LocalDate?,
    onGetName: () -> String?,
    onGetDescription: () -> String?,

    onColorChanged: (String) -> Unit,
    onGetColor: () -> String?,
    onGetBillable: () -> Boolean,
    onBillableChanged: (Boolean) -> Unit,

    onProjectsUpdated: () -> Unit
) {

    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    val startDate = remember { mutableStateOf<LocalDate?>(null) }
    val endDate = remember { mutableStateOf<LocalDate?>(null) }
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedColor = remember {
        mutableStateOf("")
    }
    val billable = remember { mutableStateOf(false) }

    if (editProject) {
        LaunchedEffect(key1 = "edit") {
            onGetProjectById()
        }
    }

    startDate.value = onGetStartDate() ?: currentDate
    endDate.value = onGetEndDate() ?: currentDate
    name.value = onGetName() ?: ""
    description.value = onGetDescription() ?: ""
    selectedColor.value = onGetColor() ?: ""
    billable.value = onGetBillable()

    Form(attrs = listOf(AuthContainerStyle).toAttrs {
        onSubmit { evt ->
            evt.preventDefault()
            onSubmitProject()
        }
    }) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Start Date")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("startdate")
                        onChange {
                            startDate.value = it.value.toLocalDate()
                            onStartDateChanged(it.value.toLocalDate())
                        }
                    }
            )
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("End Date")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("enddate")
                        onChange {
                            endDate.value = it.value.toLocalDate()
                            onEndDateChanged(it.value.toLocalDate())
                        }
                    }
            )

            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Project Name")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("projectname")
                        onChange {
                            name.value = it.value
                            onNameChanged(it.value)
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
                Text("Project is billable")
            }
            Input(
                InputType.Checkbox,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        name("billable")
                        onChange {
                            billable.value = it.value
                            onBillableChanged(it.value)
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
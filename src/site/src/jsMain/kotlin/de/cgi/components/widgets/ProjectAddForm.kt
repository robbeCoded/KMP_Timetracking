package de.cgi.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toAttrs
import de.cgi.common.projects.ProjectAddViewModel
import de.cgi.components.styles.CustomColors
import de.cgi.components.styles.Heading3
import de.cgi.components.styles.HorizontalSpacer
import de.cgi.components.styles.VerticalSpacer
import kotlinx.datetime.toLocalDate
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*


@Composable
fun ProjectAddForm(
    viewModel: ProjectAddViewModel,
    onProjectsUpdated: () -> Unit,
    onUpdate: () -> Unit,
) {
    val startDate = viewModel.startDate.collectAsState()
    val endDate = viewModel.endDate.collectAsState()
    val nameFlow = viewModel.name.collectAsState()
    val descriptionFlow = viewModel.description.collectAsState()
    val selectedColor = viewModel.color.collectAsState()
    val billable = viewModel.billable.collectAsState()

    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }


    name.value = nameFlow.value
    description.value = descriptionFlow.value ?: ""


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
                Text("New Project")
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
                        Text("Start Date")
                    }
                    Input(
                        InputType.Date,
                        attrs = listOf(InputFieldStyle)
                            .toAttrs {
                                value(startDate.value.toString())
                                onChange {
                                    viewModel.startDateChanged(it.value.toLocalDate())
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
                        Text("End Date")
                    }
                    Input(
                        InputType.Date,
                        attrs = listOf(InputFieldStyle)
                            .toAttrs {
                                value(endDate.value.toString())
                                onChange {
                                    viewModel.endDateChanged(it.value.toLocalDate())
                                }
                            }
                    )
                }
            }

            VerticalSpacer(16)

            Div(de.cgi.components.styles.Label.toAttrs()) {
                Text("Project Title")
            }
            TextInput(
                value = name.value,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        onInput {
                            name.value = it.value
                        }
                    }
            )
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckboxInput(
                    checked = (billable.value),
                    attrs = listOf(CheckBoxStyle).toAttrs
                    {
                        onChange {
                            viewModel.billableChanged(it.value)
                        }
                    }
                )
                HorizontalSpacer(16)
                Div(Heading3.toAttrs()) {
                    Text("Project is billable")
                }
            }
            VerticalSpacer(16)
            ColorSelection(
                colorChanged = viewModel::colorChanged,
                selectedColor = viewModel.color.collectAsState().value
            )
            VerticalSpacer(32)
            AddEditFormButton(
                onClick = {
                    viewModel.descriptionChanged(description.value)
                    viewModel.nameChanged(name.value)
                    viewModel.submitProject()
                    onProjectsUpdated()
                    onUpdate()
                },
                text = "Submit"
            )
        }
    }
}
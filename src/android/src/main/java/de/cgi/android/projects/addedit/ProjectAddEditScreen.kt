package de.cgi.android.projects.addedit

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.X
import de.cgi.android.ui.components.AddEditButtonSection
import de.cgi.android.ui.components.SelectableTextField
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.android.util.colorToString
import de.cgi.common.util.format
import kotlinx.datetime.*

@Composable
fun ProjectAddEditScreen(
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


    val context = LocalContext.current
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

    fun datePickerDialog(
        date: MutableState<LocalDate?>,
        onDateChanged: (LocalDate) -> Unit
    ): DatePickerDialog {
        return DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Update the state of the selected date

                date.value = LocalDate(year, month, dayOfMonth)
                onDateChanged(date.value!!)
            },
            date.value!!.year,
            date.value!!.monthNumber,
            date.value!!.dayOfMonth,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(imageVector = FeatherIcons.X, contentDescription = "Cancel")
            }
        }
        SelectableTextField(
            value = startDate.value!!.format(),
            onValueChange = { newValue ->
                startDate.value = newValue.toLocalDate()
                onStartDateChanged(newValue.toLocalDate())
            },
            label = "Start Date",
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { datePickerDialog(startDate, onStartDateChanged).show() }
        )
        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = endDate.value!!.format(),
            onValueChange = { newValue ->
                endDate.value = newValue.toLocalDate()
                onEndDateChanged(newValue.toLocalDate())
            },
            label = "End Date",
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { datePickerDialog(endDate, onEndDateChanged).show() }
        )
        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = name.value,
            onValueChange = {
                name.value = it
                onNameChanged(it)
            },
            label = "Project name",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = description.value,
            onValueChange = {
                description.value = it
                onDescriptionChanged(it)
            },
            label = "Description",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = billable.value,
                onCheckedChange = {
                    onBillableChanged(!billable.value)
                    billable.value = !billable.value
                }
            )
            Text(text = "Project is billable", modifier = Modifier.padding(start = 8.dp))

        }

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Text(text = "Color")
        Spacer(modifier = Modifier.height(LocalSpacing.current.small))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LocalColor.current.projectColorsList.forEach { color ->
                val colorString = colorToString(color)
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .border(
                            width = if (selectedColor.value == colorString) 3.dp else 1.dp,
                            color = if (selectedColor.value == colorString) LocalColor.current.actionPrimary else LocalColor.current.black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(color)
                        .clickable {
                            selectedColor.value = colorString
                            onColorChanged(colorString)
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        AddEditButtonSection(
            edit = editProject,
            onSubmit = {
                onSubmitProject()
                onProjectsUpdated()

            },
            onUpdate = {
                onUpdateProject()
                onProjectsUpdated()

            },
            onDelete = {
                onDeleteProject()
                onProjectsUpdated()

            },
            onNavigateBack = { onNavigateBack() }
        )
    }
}



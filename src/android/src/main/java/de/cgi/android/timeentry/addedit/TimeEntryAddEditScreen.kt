package de.cgi.android.timeentry.addedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.X
import de.cgi.android.ui.components.AddEditButtonSection
import de.cgi.android.ui.components.ProjectDropdownMenu
import de.cgi.android.ui.components.SelectableTextField
import de.cgi.common.util.format
import kotlinx.datetime.*

@Composable
fun TimeEntryAddEditScreen(
    onDateChanged: (LocalDate) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onDurationChanged: (LocalTime) -> Unit,
    onProjectChanged: (String, String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSubmitTimeEntry: () -> Unit,
    onDeleteTimeEntry: () -> Unit,
    onUpdateTimeEntry: () -> Unit,
    onNavigateBack: () -> Unit,
    onGetTimeEntryById: () -> Unit,
    editTimeEntry: Boolean,
    onGetStartTime: () -> LocalTime?,
    onGetEndTime: () -> LocalTime?,
    onGetDuration: () -> LocalTime?,
    onGetDate: () -> LocalDate?,
    onGetDescription: () -> String?,
    onGetProjectId: () -> String?,
    onGetProjectName: () -> String?,
) {

    val context = LocalContext.current
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin"))



    if (editTimeEntry) {
        LaunchedEffect(key1 = "edit") {
            onGetTimeEntryById()
        }
    }


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
    //TODO: initialize project name value

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

                date.value = LocalDate(year, month + 1, dayOfMonth)
                onDateChanged(date.value!!)
            },
            date.value!!.year,
            date.value!!.monthNumber.minus(1),
            date.value!!.dayOfMonth,
        )
    }

    fun timePickerDialog(
        time: MutableState<LocalTime?>,
        onTimeChanged: (LocalTime) -> Unit,
    ): TimePickerDialog {
        return TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                    time.value = LocalTime(hourOfDay, minute)
                    onTimeChanged(time.value!!)
            },
            time.value!!.hour,
            time.value!!.minute,
            true
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SelectableTextField(
                value = date.value!!.format(),
                onValueChange = { newValue ->
                    date.value = newValue.toLocalDate()
                    onDateChanged(newValue.toLocalDate())
                },
                label = "Date",
                onClick = { datePickerDialog.show() },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            SelectableTextField(
                value = duration.value.toString(),
                onValueChange = { newValue ->
                    duration.value = newValue.toLocalTime()
                    onDurationChanged(
                        newValue.toLocalTime(),
                    )
                },
                label = "Duration",
                onClick = { timePickerDialog(duration, onDurationChanged).show() },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SelectableTextField(
                value = startTime.value.toString(),
                onValueChange = { newValue ->
                    startTime.value = newValue.toLocalTime()
                    onStartTimeChanged(newValue.toLocalTime())
                },
                label = "Start time",
                modifier = Modifier.weight(1f),
                onClick = { timePickerDialog(startTime, onStartTimeChanged).show() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            SelectableTextField(
                value = endTime.value.toString(),
                onValueChange = { newValue ->
                    endTime.value = newValue.toLocalTime()
                    onEndTimeChanged(newValue.toLocalTime())
                },
                label = "End time",
                modifier = Modifier.weight(1f),
                onClick = { timePickerDialog(endTime, onEndTimeChanged).show() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = description.value,
            onValueChange = {
                description.value = it
                onDescriptionChanged(it)
            },
            label = "Description",
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProjectDropdownMenu(selectedProject, onProjectChanged)

        Spacer(modifier = Modifier.height(32.dp))

        AddEditButtonSection(
            edit = editTimeEntry,
            onSubmit = { onSubmitTimeEntry() },
            onUpdate = { onUpdateTimeEntry() },
            onDelete = { onDeleteTimeEntry() },
            onNavigateBack = { onNavigateBack() })
    }
}

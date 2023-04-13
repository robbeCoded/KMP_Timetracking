package de.cgi.android.timeentry

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.cgi.android.ui.components.ProjectDropdownMenu
import de.cgi.android.ui.components.SelectableTextField
import de.cgi.android.ui.components.WeekdayHeader
import de.cgi.common.ResultState
import de.cgi.common.data.model.Project
import kotlinx.datetime.*

//interfaces für viewModel
//Adapter pattern anschauen
//strategy pattern
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeEntryAddEditScreen(
    projectListState: ResultState<List<Project>>,
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
    onGetProjects: () -> Unit
) {

    val context = LocalContext.current
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin"))
    val selectedProject = remember { mutableStateOf("") }


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
    val description = remember { mutableStateOf("") }

    date.value = onGetDate() ?: currentDateTime.date
    startTime.value = onGetStartTime() ?: currentDateTime.time
    endTime.value = onGetEndTime() ?: currentDateTime.time
    duration.value = onGetDuration() ?: LocalTime(0, 0)
    project.value = onGetProjectId() ?: ""
    description.value = onGetDescription() ?: ""

    val datePickerDialog = remember {
        DatePickerDialog(
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

    fun timePickerDialog(
        time: MutableState<LocalTime?>,
        onTimeChanged: (LocalTime) -> Unit
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
        verticalArrangement = Arrangement.Center,
    ) {

        SelectableTextField(
            value = date.value.toString(),
            onValueChange = { newValue ->
                date.value = newValue.toLocalDate()
                onDateChanged(newValue.toLocalDate())
            },
            label = "Date",
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { datePickerDialog.show() }
        )
        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = startTime.value.toString(),
            onValueChange = { newValue ->
                startTime.value = newValue.toLocalTime()
                onStartTimeChanged(newValue.toLocalTime())
            },
            label = "Start time",
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { timePickerDialog(startTime, onStartTimeChanged).show() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = duration.value.toString(),
            onValueChange = { newValue ->
                duration.value = newValue.toLocalTime()
                onDurationChanged(
                    newValue.toLocalTime()
                )
            },
            label = "Duration",
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { timePickerDialog(duration, onDurationChanged).show() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = endTime.value.toString(),
            onValueChange = { newValue ->
                endTime.value = newValue.toLocalTime()
                onEndTimeChanged(newValue.toLocalTime())
            },
            label = "End time",
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { timePickerDialog(endTime, onEndTimeChanged).show() }
        )
        Spacer(modifier = Modifier.height(16.dp))

        ProjectDropdownMenu(projectListState, selectedProject, onProjectChanged, onGetProjects)

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = description.value,
            onValueChange = {
                description.value = it
                onDescriptionChanged(it)
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if(editTimeEntry) {
                Button(
                    onClick = {
                        onUpdateTimeEntry()
                        onNavigateBack()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(),
                ) {
                    Text("Update")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        onDeleteTimeEntry()
                        onNavigateBack()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(),
                ) {
                    Text("Delete")
                }
            } else {
                Button(
                    onClick = {
                        onSubmitTimeEntry()
                        onNavigateBack()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(),
                ) {
                    Text("Submit")
                }
            }

        }
    }
}



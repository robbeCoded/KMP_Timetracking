package de.cgi.android.timeentry

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeEntryEditScreen(
    onDateChanged: (LocalDate) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onDurationChanged: (LocalTime) -> Unit,
    onProjectChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSubmitTimeEntry: () -> Unit,
    onDeleteTimeEntry: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    // State variables for each input field
    val date = remember { mutableStateOf(LocalDate(1,1,1)) }
    val startTime = remember { mutableStateOf(LocalTime(1,1)) }
    val endTime = remember { mutableStateOf(LocalTime(1,1)) }
    val duration = remember { mutableStateOf(LocalTime(1,1)) }
    val project = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Update the state of the selected date

                date.value = LocalDate(year, month, dayOfMonth)
                onDateChanged(date.value)
            },
            date.value.year,
            date.value.monthNumber,
            date.value.dayOfMonth,
        )
    }

    val startTimePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                startTime.value = LocalTime(hourOfDay, minute)
                onStartTimeChanged(startTime.value)
            },
            startTime.value.hour,
            startTime.value.minute,
            true
        )
    }

    fun timePickerDialog(
        time: MutableState<LocalTime>,
        onTimeChanged: (LocalTime) -> Unit
    ): TimePickerDialog {
        return TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                time.value = LocalTime(hourOfDay, minute)
                onTimeChanged(time.value)
            },
            time.value.hour,
            time.value.minute,
            true
        )
    }
    val endTimePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                endTime.value = LocalTime(hourOfDay, minute)
                onEndTimeChanged(endTime.value)
            },
            endTime.value.hour,
            endTime.value.minute,
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
            onClick = { timePickerDialog(startTime, onStartTimeChanged) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = endTime.value.toString(),
            onValueChange = { newValue ->
                endTime.value = newValue.toLocalTime()
                onStartTimeChanged(newValue.toLocalTime())
            },
            label = "End time",
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { timePickerDialog(endTime, onEndTimeChanged) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectableTextField(
            value = endTime.value.toString(),
            onValueChange = { newValue ->
                endTime.value = newValue.toLocalTime()
                onStartTimeChanged(newValue.toLocalTime())
            },
            label = "End time",
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { timePickerDialog(duration, onDurationChanged) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = project.value,
            onValueChange = {
                project.value = it
                onProjectChanged(it)
            },
            label = { Text("Project") },
            modifier = Modifier.fillMaxWidth()
        )
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    onClick: () -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        enabled = false
    )
}


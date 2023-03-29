package de.cgi.android.timetracking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.cgi.android.destinations.TimeEntryScreenDestination
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddTimeEntryScreen(
    viewModel: TimeEntryViewModel = getViewModel<TimeEntryViewModel>(),
    navigator: DestinationsNavigator
) {
    val scope = rememberCoroutineScope()

    val (day, setDay) = remember { mutableStateOf(LocalDate.) }
    val (startTime, setStartTime) = remember { mutableStateOf(LocalTime.now()) }
    val (endTime, setEndTime) = remember { mutableStateOf(LocalTime.now()) }
    val (project, setProject) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Day")
            IconButton(onClick = {
                scope.launch {
                    val newDay = showDatePicker(day, scope)
                    if (newDay != null) {
                        setDay(newDay)
                    }
                }
            }) {
                Icon(Icons.Filled.DateRange, contentDescription = "Select Day")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Start Time")
            IconButton(onClick = {
                scope.launch {
                    val newStartTime = showTimePicker(startTime, scope)
                    if (newStartTime != null) {
                        setStartTime(newStartTime)
                    }
                }
            }) {
                Icon(Icons.Filled.Schedule, contentDescription = "Select Start Time")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "End Time")
            IconButton(onClick = {
                scope.launch {
                    val newEndTime = showTimePicker(endTime, scope)
                    if (newEndTime != null) {
                        setEndTime(newEndTime)
                    }
                }
            }) {
                Icon(Icons.Filled.Schedule, contentDescription = "Select End Time")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = project,
            onValueChange = setProject,
            label = { Text("Project") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = description,
            onValueChange = setDescription,
            label = { Text("Description") }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                viewModel.addTimeEntry(
                    TimeEntry(
                        day = day,
                        startTime = startTime,
                        endTime = endTime,
                        project = project,
                        description = description
                    )
                )
                navigator.navigateUp()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = project.isNotBlank() && description.isNotBlank()
        ) {
            Text(text = "Add")
        }
    }
}

private suspend fun showDatePicker(initialValue: LocalDate, scope: CoroutineScope): LocalDate? {
    return showDatePickerDialog(
        initialValue,
        onDateSelected = { date -> },
        onCancel = { scope.cancel() }
    )
}

private suspend fun showTimePicker(initialValue: LocalTime, scope: CoroutineScope):

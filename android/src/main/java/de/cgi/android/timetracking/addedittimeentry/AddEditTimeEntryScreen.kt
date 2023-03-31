package de.cgi.android.timetracking.addedittimeentry

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.cgi.android.navigation.Screen
import de.cgi.android.timetracking.addedittimeentry.components.SelectableTextField
import de.cgi.common.data.model.TimeEntry
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.koin.androidx.compose.getViewModel
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditTimeEntryScreen(
    viewModel: AddEditTimeEntryViewModel = getViewModel<AddEditTimeEntryViewModel>(),
    navController: NavController,
    timeEntryId: String?
) {
    val title = "Timetracking"

    val timeEntry = remember { mutableStateOf<TimeEntry?>(null) }

    val scope = rememberCoroutineScope()
    val mDate = remember { mutableStateOf(LocalDate(1,1,1)) }
    val mStartTime = remember { mutableStateOf(LocalTime(0,0)) }
    val mEndTime = remember { mutableStateOf(LocalTime(0,0)) }
    val mContext = LocalContext.current

    //TODO: clean this up! logic in presentation
    if (timeEntryId != null) {
        viewModel.onEvent(AddEditTimeEnryUIEvent.EditTimeEntry(id = timeEntryId))
        LaunchedEffect(viewModel, timeEntryId) {
            if (timeEntry.value == null) {
                viewModel.onEvent(AddEditTimeEnryUIEvent.EditTimeEntry(timeEntryId))
                viewModel.result.collect { result ->
                    timeEntry.value = result
                }
            }
        }
    } else {
        // initialize with current date/time
        val now = Clock.System.now()
        val timeZone = TimeZone.of("UTC+2")
        val localDateTime = now.toLocalDateTime(timeZone)
        mDate.value = localDateTime.date
        mStartTime.value = localDateTime.time
        mEndTime.value = LocalTime(mStartTime.value.hour.plus(1), mStartTime.value.minute)
    }

val datePickerDialog = remember {
    DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Update the state of the selected date

            mDate.value = LocalDate(year, month, dayOfMonth)
            viewModel.onEvent(AddEditTimeEnryUIEvent.TimeEntryDayChanged(mDate.value))
        },
        mDate.value.year,
        mDate.value.monthNumber,
        mDate.value.dayOfMonth,
    )
}

val startTimePickerDialog = remember {
    TimePickerDialog(
        mContext,
        { _, hourOfDay, minute ->
            mStartTime.value = LocalTime(hourOfDay, minute)
            viewModel.onEvent(AddEditTimeEnryUIEvent.TimeEntryStartChanged(mStartTime.value))
        },
        mStartTime.value.hour,
        mStartTime.value.minute,
        true
    )
}

val endTimePickerDialog = remember {
    TimePickerDialog(
        mContext,
        { _, hourOfDay, minute ->
            mEndTime.value = LocalTime(hourOfDay, minute)
            viewModel.onEvent(AddEditTimeEnryUIEvent.TimeEntryEndChanged(mEndTime.value))
        },
        mEndTime.value.hour,
        mEndTime.value.minute,
        true
    )
}

Scaffold(
topBar = {
    de.cgi.android.ui.components.AppBar(
        onNavigationIconClick = {
            scope.launch {

            }
        },
        title = title,
    )
}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        // Text input field for the "Project" field
        TextField(
            value = viewModel.state.project,
            onValueChange = { viewModel.onEvent(AddEditTimeEnryUIEvent.TimeEntryProjectChanged(it)) },
            label = { Text("Project") },
            modifier = Modifier.fillMaxWidth()
        )
        // Text input field for the "Description" field
        TextField(
            value = viewModel.state.description,
            onValueChange = {
                viewModel.onEvent(
                    AddEditTimeEnryUIEvent.TimeEntryDescriptionChanged(it)
                )
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        // Text input field for the "Day" field with datepicker
        SelectableTextField(
            textValue = mDate.value.toString(),
            onClick = { datePickerDialog.show() },
            label = "Day",
        )

        // Text input fields for the "Start Time" and "End Time" fields with time picker
        SelectableTextField(
            textValue = mStartTime.value.toString(),
            onClick = { startTimePickerDialog.show() },
            label = "Start Time",

            )

        SelectableTextField(
            textValue = mEndTime.value.toString(),
            onClick = { endTimePickerDialog.show() },
            label = "End Time",
        )

        // Button for submitting the time entry
        Button(
            onClick = {
                viewModel.onEvent(AddEditTimeEnryUIEvent.TimeEntryStartChanged(mStartTime.value))
                viewModel.onEvent(AddEditTimeEnryUIEvent.TimeEntryDayChanged(mDate.value))
                viewModel.onEvent(AddEditTimeEnryUIEvent.TimeEntryEndChanged(mEndTime.value))
                viewModel.onEvent(AddEditTimeEnryUIEvent.SubmitTimeEntry)
                navController.navigate(Screen.TimeEntryScreen.route)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Submit")
        }

        if(timeEntryId != null) {
            Button(
                onClick = {
                    viewModel.onEvent(AddEditTimeEnryUIEvent.DeleteTimeEntry(timeEntryId))
                    navController.navigate(Screen.TimeEntryScreen.route)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Delete")
            }
        }

    }
}
}


package de.cgi.android.timetracking.newTimeEntry

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import de.cgi.android.auth.AuthUiEvent
import de.cgi.android.timetracking.TimeEntryViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.koin.androidx.compose.getViewModel
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination
fun AddTimeEntryScreen(
    viewModel: AddTimeEntryViewModel = getViewModel<AddTimeEntryViewModel>(),
    navigator: DestinationsNavigator,
) {
    val calendar = Calendar.getInstance()
    val title = "Add time entry"

    val now: Instant = Clock.System.now()
    val today: LocalDate = now.toLocalDateTime(TimeZone.of("UTC+2")).date
    val startTime =  LocalTime(
        now.toLocalDateTime(TimeZone.of("UTC+2")).time.hour,
        now.toLocalDateTime(TimeZone.of("UTC+2")).time.minute
    )
    val endTime = LocalTime(
        now.toLocalDateTime(TimeZone.of("UTC+2")).time.hour.plus(1),
        now.toLocalDateTime(TimeZone.of("UTC+2")).time.minute
    )

    val scope = rememberCoroutineScope()
    val mDate = remember { mutableStateOf(today) }
    val mStartTime = remember { mutableStateOf(startTime) }
    val mEndTime = remember { mutableStateOf(endTime) }
    val mContext = LocalContext.current


    // Initializing a Calendar

    // Initializing a DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val datePickerDialog = remember {
        DatePickerDialog(
            mContext,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Update the state of the selected date

                mDate.value = LocalDate(year, month, dayOfMonth)
                viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryDayChanged(mDate.value))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val startTimePickerDialog = remember {
        TimePickerDialog(
            mContext,
            { _, hourOfDay, minute ->
                mStartTime.value = LocalTime(hourOfDay, minute)
                viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryStartChanged(mStartTime.value))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    val endTimePickerDialog = remember {
        TimePickerDialog(
            mContext,
            { _, hourOfDay, minute ->
                mEndTime.value = LocalTime(hourOfDay, minute)
                viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryEndChanged(mEndTime.value))
            },
            mStartTime.value.hour.plus(1),
            mStartTime.value.minute,
            true
        )
    }

    Scaffold(
        topBar = {
            de.cgi.android.ui.components.AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        navigator.navigateUp()
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
                value = "641d6db09c10b647af336552",
                onValueChange = { viewModel.onEvent(AddTimeEntryUiEvent.TimeEntryProjectChanged(it)) },
                label = { Text("Project") },
                modifier = Modifier.fillMaxWidth()
            )
            // Text input field for the "Description" field
            TextField(
                value = "Keine Beschreibung",
                onValueChange = {
                    viewModel.onEvent(
                        AddTimeEntryUiEvent.TimeEntryDescriptionChanged(
                            it
                        )
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
                    viewModel.onEvent(AddTimeEntryUiEvent.newTimeEntry)
                    navigator.navigateUp()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Submit")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableTextField(
    modifier: Modifier = Modifier,
    textValue: String,
    label: String,
    onClick: () -> Unit,
) {
    TextField(
        value = textValue,
        onValueChange = { },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        enabled = false
    )
}
package de.cgi.android.projects

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.cgi.android.ui.components.SelectableTextField
import kotlinx.datetime.*

@OptIn(ExperimentalMaterial3Api::class)
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
) {

    val context = LocalContext.current
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date


    if (editProject) {
        LaunchedEffect(key1 = "edit") {
            onGetProjectById()
        }
    }

    val startDate = remember { mutableStateOf<LocalDate?>(null) }
    val endDate = remember { mutableStateOf<LocalDate?>(null) }
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    startDate.value = onGetStartDate() ?: currentDate
    endDate.value = onGetEndDate() ?: currentDate
    name.value = onGetName() ?: ""
    description.value = onGetDescription() ?: ""

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
        verticalArrangement = Arrangement.Center,
    ) {
        SelectableTextField(
            value = startDate.value.toString(),
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
            value = endDate.value.toString(),
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

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
                onNameChanged(it)
            },
            label = { Text("Project name") },
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
            if(editProject) {
                Button(
                    onClick = {
                        onUpdateProject()
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
                        onDeleteProject()
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
                        onSubmitProject()
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


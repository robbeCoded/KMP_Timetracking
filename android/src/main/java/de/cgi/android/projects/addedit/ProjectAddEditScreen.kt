package de.cgi.android.projects.addedit

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.CheckCircle
import compose.icons.feathericons.RefreshCcw
import compose.icons.feathericons.Trash
import compose.icons.feathericons.X
import de.cgi.android.ui.components.AddEditButtonSection
import de.cgi.android.ui.components.SelectableTextField
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.util.format
import kotlinx.datetime.*

@RequiresApi(Build.VERSION_CODES.O)
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
        Spacer(modifier = Modifier.height(16.dp))

        AddEditButtonSection(
            edit = editProject,
            onSubmit = { onSubmitProject() },
            onUpdate = { onUpdateProject() },
            onDelete = { onDeleteProject() },
            onNavigateBack = { onNavigateBack() }
        )
    }
}



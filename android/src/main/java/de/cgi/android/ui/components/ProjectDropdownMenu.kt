package de.cgi.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.cgi.android.util.AsyncData
import de.cgi.common.ResultState
import de.cgi.common.data.model.Project

@Composable
fun ProjectDropdownMenu(
    selectedProject: MutableState<String>,
    onProjectChanged: (String, String) -> Unit,
    onGetProjects: () -> ResultState<Map<String, String>?>
) {
    val expandedProject = remember { mutableStateOf(false) }
    val projectListState = onGetProjects()

    Box {
        Text(
            text = selectedProject.value,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expandedProject.value = true
                }
                .padding(vertical = 12.dp)
                .background(
                    MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                    RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colors.onSurface
        )
        DropdownMenu(
            expanded = expandedProject.value,
            onDismissRequest = { expandedProject.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            when (val resultState = projectListState) {
                is ResultState.Success -> {
                    val projectList = resultState.data
                    projectList?.forEach { project ->
                        DropdownMenuItem(
                            onClick = {
                                selectedProject.value = project.value
                                onProjectChanged(project.key, project.value)
                                expandedProject.value = false
                            },
                            text = { Text(project.value) }
                        )
                    }
                }
                is ResultState.Error -> {
                    DropdownMenuItem(
                        text = { Text("Error loading projects. Click to try again.") },
                        onClick = { onGetProjects() }
                    )
                }
                is ResultState.Loading -> {
                    DropdownMenuItem(
                        text = { Text("Loading...") },
                        onClick = { }
                    )
                }
            }
        }
    }
}




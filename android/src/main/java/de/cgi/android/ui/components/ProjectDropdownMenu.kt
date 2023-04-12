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
    projectListState: ResultState<List<Project>>,
    selectedProject: MutableState<String>,
    onProjectChanged: (String, String) -> Unit,
    onGetProjects: () -> Unit,
) {
    val expandedProject = remember { mutableStateOf(false) }

    Box {
        Text(
            text = selectedProject.value,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (projectListState is ResultState.Loading) {
                        onGetProjects()
                    }
                    expandedProject.value = true
                }
                .padding(vertical = 12.dp)
                .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f), RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colors.onSurface
        )
        DropdownMenu(
            expanded = expandedProject.value,
            onDismissRequest = { expandedProject.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncData(
                resultState = projectListState,
                errorContent = {
                    Text(text = "Error loading projects")
                }
            ) { projectList ->
                projectList?.forEach { project ->
                    DropdownMenuItem(
                        onClick = {
                            selectedProject.value = project.name
                            onProjectChanged(project.id, project.name)
                            expandedProject.value = false
                        },
                        text = { Text(project.name) }
                    )
                }
            }
        }
    }
}



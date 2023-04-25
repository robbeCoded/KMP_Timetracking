package de.cgi.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.cgi.common.ResultState
import de.cgi.common.repository.ProjectMapProvider
import org.koin.androidx.compose.get

@Composable
fun ProjectDropdownMenu(
    selectedProject: MutableState<String>,
    onProjectChanged: (String, String) -> Unit,
) {
    val expandedProject = remember { mutableStateOf(false) }
    val projectMapProvider = get<ProjectMapProvider>()
    val projectListState = projectMapProvider.getProjectNameMapValue()

    Column(modifier = Modifier.wrapContentSize()) {
        Text(text = "Project")
        Spacer(modifier = Modifier.height(5.dp))
        SelectableTextField(
            value = selectedProject.value,
            onValueChange = { },
            label = "",
            onClick = { expandedProject.value = true }
        )
        DropdownMenu(
            expanded = expandedProject.value,
            onDismissRequest = { expandedProject.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            when (projectListState) {
                is ResultState.Success -> {
                    val projectList = projectListState.data
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
                        onClick = { projectMapProvider.notifyProjectUpdates() }
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


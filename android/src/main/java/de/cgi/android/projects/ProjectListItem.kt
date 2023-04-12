package de.cgi.android.projects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.cgi.common.data.model.Project
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

@Composable
fun ProjectListItem(
    project: Project,
    onClick: (Project) -> Unit,
    onDeleteProject: (Project) -> Unit,
) {
    val startDate: LocalDate = project.startDate.toLocalDate()
    val endDate: LocalDate = project.endDate.toLocalDate()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick(project) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(text = project.description ?: "Keine Beschreibung")

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "$startDate - $endDate"
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(text = project.id)

        }
    }
}
package de.cgi.android.projects.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.cgi.common.data.model.Project
import de.cgi.common.util.format
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
            Text(text = project.name, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp))
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = project.description ?: "Keine Beschreibung")
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${startDate.format()} - ${endDate.format()}"
            )

        }
    }
}
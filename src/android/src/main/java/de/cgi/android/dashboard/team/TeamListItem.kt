package de.cgi.android.dashboard.team

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.common.data.model.Team

@Composable
fun TeamListItem(
    team: Team,
    onClick: (Team) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick(team) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(LocalSpacing.current.small))
            Text(text = team.name)
            Spacer(modifier = Modifier.height(LocalSpacing.current.small))
        }
    }
}
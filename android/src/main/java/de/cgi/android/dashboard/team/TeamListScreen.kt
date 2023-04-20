package de.cgi.android.dashboard.team

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import de.cgi.android.util.AsyncData
import de.cgi.android.util.GenericError
import de.cgi.common.ResultState
import de.cgi.common.data.model.Team
import kotlinx.datetime.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TeamListScreen(
    teamListState: ResultState<List<Team>>,
    reloadTeams: () -> Unit,
    onNewTeamClick: () -> Unit,
    onTeamClick: (Team) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = onNewTeamClick) {
                Icon(imageVector = FeatherIcons.Plus, contentDescription = "New team")
            }
        }) {

        Box(Modifier.padding(it)) {
            AsyncData(resultState = teamListState, errorContent = {
                GenericError(
                    onDismissAction = reloadTeams
                )
            }) { teamList ->
                teamList?.let {
                    if (teamList.isNotEmpty()) {
                        LazyColumn {
                            items(teamList, key = { team -> team.id }) { item ->
                                TeamListItem(
                                    onClick = onTeamClick,
                                    team = item,
                                )

                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 10.dp),
                        ) {
                            Text(
                                text = "You are not part of a team yet. Create a team by clicking on the +.",

                                modifier = Modifier.align(
                                    Alignment.Center
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

        }
    }
}

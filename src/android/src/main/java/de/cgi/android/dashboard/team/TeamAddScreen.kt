package de.cgi.android.dashboard.team

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.cgi.android.timeentry.list.TimeEntryListItem
import de.cgi.android.ui.components.WeekdayHeader
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.util.AsyncData
import de.cgi.android.util.GenericError
import de.cgi.common.ResultState
import de.cgi.common.data.model.User

@Composable
fun TeamAddScreen(
    userListState: ResultState<List<User>>,
    onReloadUserList: () -> Unit,
    onSubmit: (String, List<String>) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    var teamName by remember { mutableStateOf("") }
    val selectedUserIds = remember { mutableStateListOf<String>() }

    Scaffold(
        scaffoldState = scaffoldState,

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {

            TextField(
                value = teamName,
                onValueChange = { teamName = it },
                label = { Text("Team Name") },
                modifier = Modifier.padding(16.dp)
            )

            Box(Modifier.padding(it)) {
                AsyncData(resultState = userListState, errorContent = {
                    GenericError(
                        onDismissAction = onReloadUserList
                    )
                }) { userList ->
                    userList?.let {
                        if (userList.isNotEmpty()) {
                            LazyColumn {
                                items(userList, key = { user -> user.id }) { item ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { onUserClicked(item, selectedUserIds) },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = selectedUserIds.contains(item.id),
                                            onCheckedChange = { onUserClicked(item, selectedUserIds) }
                                        )
                                        Text(text = item.name, modifier = Modifier.padding(start = 8.dp))
                                    }
                                }
                            }
                            Button(
                                onClick = { onSubmit(teamName, selectedUserIds) },
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Text("Submit")
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(all = 10.dp),
                            ) {
                                Text(
                                    text = "No Users where found.",

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
}

private fun onUserClicked(user: User, selectedUserIds: MutableList<String>) {
    if (selectedUserIds.contains(user.id)) {
        selectedUserIds.remove(user.id)
    } else {
        selectedUserIds.add(user.id)
    }
}
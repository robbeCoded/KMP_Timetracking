package de.cgi.android.dashboard.team

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {

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
                                    Text(text = item.name)
                                }
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
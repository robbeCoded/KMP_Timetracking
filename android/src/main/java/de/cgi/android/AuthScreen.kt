package de.cgi.android

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.cgi.android.destinations.AuthScreenDestination
import de.cgi.android.destinations.TimeEntryScreenDestination

import de.cgi.android.viewmodel.AuthViewModel
import de.cgi.common.data.model.responses.AuthResult
import org.koin.androidx.compose.getViewModel


@RootNavGraph(start = true)
@Destination
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel = getViewModel<AuthViewModel>()
) {
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.authResult.collect { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    navigator.navigate(TimeEntryScreenDestination()) {
                        popUpTo(AuthScreenDestination.route) {
                            inclusive = true
                        }
                    }

                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "You're not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.signUpName,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignUpNameChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Name")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.signUpEmail,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignUpEmailChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.signUpPassword,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(AuthUiEvent.SignUp)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign up")
        }

        Spacer(modifier = Modifier.height(64.dp))

        TextField(
            value = state.signInEmail,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignInEmailChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.signInPassword,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(AuthUiEvent.SignIn)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign in")
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
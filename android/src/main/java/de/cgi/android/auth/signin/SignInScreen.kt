package de.cgi.android.auth.signin


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import de.cgi.R
import de.cgi.android.auth.InputType
import de.cgi.android.auth.SignInState
import de.cgi.android.ui.components.TextInput
import de.cgi.android.ui.components.showToast
import de.cgi.android.ui.theme.LocalColor

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun SignInScreen(
    signInState: SignInState?,
    onSignInEmailChanged: (String) -> Unit,
    onSignInPasswordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignInSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onGetUserRole: () -> Unit
    ) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val backgroundImage = painterResource(R.drawable.login_background)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = "Background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            alpha = 0.6F
        )
        Column(
            Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = FeatherIcons.Clock,
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp),
                tint = Color.White
            )

            TextInput(
                InputType.EMail,
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }),
                onValueChanged = { onSignInEmailChanged(it) })
            TextInput(
                InputType.Password,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onSignInClick()
                    }),
                focusRequester = passwordFocusRequester,
                onValueChanged = { onSignInPasswordChanged(it) })
            Button(onClick = {
                onSignInClick()
            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = LocalColor.current.actionPrimary)) {
                Text("SIGN IN", Modifier.padding(vertical = 8.dp))
            }
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Don't have an account?",
                    color = LocalColor.current.black,
                    fontSize = 20.sp
                )
                TextButton(onClick = { onSignUpClick() }) {
                    Text(
                        text = "Sign up",
                        style = TextStyle(
                            color = LocalColor.current.darkBlue
                        ),
                        fontSize = 20.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }

        LaunchedEffect(key1 = signInState) {
            when (signInState) {
                is SignInState.Loading -> {
                }
                is SignInState.Authorized -> {
                    context.showToast("Successfully logged in")
                    onSignInSuccess()
                    onGetUserRole()
                }
                is SignInState.Success -> {
                    context.showToast("Successfully logged in")
                    onSignInSuccess()
                    onGetUserRole()
                }
                is SignInState.Failure -> {
                    context.showToast(signInState.message ?: "Unknown error occurred")
                }
                is SignInState.Error -> {
                    context.showToast(signInState.error?: "Unknown error occurred")
                }
                else -> {
                    context.showToast("Unknown Error occurred")
                }
            }
        }
    }


}







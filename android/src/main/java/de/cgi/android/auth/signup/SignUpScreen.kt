package de.cgi.android.auth.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
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
import de.cgi.android.auth.SignUpState
import de.cgi.android.auth.showToast
import de.cgi.android.ui.components.TextInput
import de.cgi.android.ui.theme.LocalColor
import de.cgi.common.data.model.responses.AuthResult

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun SignUpScreen(
    signUpState: SignUpState?,
    isEmailValid: () -> Boolean,
    isPasswordValid: () -> Boolean,
    onSignUpPasswordChanged: (String) -> Unit,
    onSignUpEmailChanged: (String) -> Unit,
    onSignUpNameChanged: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignUpSuccess: () -> Unit,
) {

    val signUpEmailError = remember { mutableStateOf(false) }
    val signUpPasswordError = remember { mutableStateOf(false) }
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
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
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
                onValueChanged = { onSignUpEmailChanged(it) })
            AnimatedVisibility(visible = signUpEmailError.value) {
                Text(
                    text = "Not a valid e-mail address",
                    style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
                )
            }
            TextInput(
                InputType.Name,
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }),
                onValueChanged = { onSignUpNameChanged(it) })


            TextInput(
                InputType.Password,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onSignUpClick()
                    }),
                focusRequester = passwordFocusRequester,
                onValueChanged = { onSignUpPasswordChanged(it) })
            AnimatedVisibility(visible = signUpPasswordError.value) {
                Text(
                    text = "Password must be at least 8 characters",
                    style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
                )
            }

            Button(
                onClick = {
                    if (isEmailValid() && isPasswordValid()) {
                        onSignUpClick()
                        signUpPasswordError.value = false
                        signUpEmailError.value = false
                    } else {
                        signUpPasswordError.value = true
                        signUpEmailError.value = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = LocalColor.current.actionPrimary)
            ) {
                Text("SIGN UP", Modifier.padding(vertical = 8.dp))
            }
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Already have an Account?",
                    color = LocalColor.current.black,
                    fontSize = 20.sp
                )
                TextButton(onClick = { onSignUpSuccess() }) {
                    Text(
                        text = "Sign in",
                        style = TextStyle(
                            color = LocalColor.current.darkBlue
                        ),
                        fontSize = 20.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }

        LaunchedEffect(key1 = signUpState) {
            when (signUpState) {
                is SignUpState.Loading -> {
                    context.showToast("Loading")
                }
                is SignUpState.Success -> {
                    context.showToast("Successfully Signed Up")
                    onSignUpSuccess()
                }
                is SignUpState.Failure -> {
                    context.showToast(signUpState.message ?: "Unknown error occurred")
                }
                is SignUpState.Error -> {
                    context.showToast(signUpState.error ?: "Unknown error occurred")
                }
                else -> {
                    context.showToast("Unknown error occurred")
                }
            }
        }
    }
}
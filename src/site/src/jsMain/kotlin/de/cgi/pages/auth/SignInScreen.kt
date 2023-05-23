package de.cgi.pages.auth

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.auth.SignInState
import de.cgi.common.auth.SignInViewModel
import de.cgi.components.styles.MainButtonStyle
import de.cgi.components.styles.Theme
import de.cgi.components.widgets.AuthContainerStyle
import de.cgi.components.widgets.InputFieldStyleSmall
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page(routeOverride = "signin")
@Composable
fun SingInScreen() {
    val di = localDI()
    val viewModel: SignInViewModel by di.instance()
    val signInState by viewModel.signInState.collectAsState()
    val ctx = rememberPageContext()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SignInForm(
            onSignInClick = viewModel::signIn,
            onSignInEmailChanged = viewModel::signInEmailChanged,
            onSignInPasswordChanged = viewModel::signInPasswordChanged
        )
        Spacer()
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically){
            Text("Don't have an account?")
            com.varabyte.kobweb.silk.components.forms.Button(onClick = { ctx.router.navigateTo("/auth/signup") }) {
                Text("Sign up")
            }
        }

    }

    when(signInState) {
        is SignInState.Success -> { ctx.router.navigateTo("/timeentry/list") }
        is SignInState.Loading -> { }
        is SignInState.Failure -> { }
        is SignInState.Error -> { }
        else -> {}
    }
}


@Composable
fun SignInForm(
    onSignInClick: () -> Unit,
    onSignInEmailChanged: (String) -> Unit,
    onSignInPasswordChanged: (String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Form(attrs = listOf(AuthContainerStyle).toAttrs {
        onSubmit { evt ->
            evt.preventDefault()
            onSignInClick()
        }
    }) {
        Column(Modifier.fillMaxSize()) {
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("E-Mail")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyleSmall)
                    .toAttrs {
                        placeholder("E-Mail Address")
                        name("email")
                        onChange {
                            email = it.value
                            onSignInEmailChanged(it.value)
                        }
                    }
            )

            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Password")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyleSmall)
                    .toAttrs {
                        placeholder("Password")
                        name("password")
                        onChange {
                            password = it.value
                            onSignInPasswordChanged(it.value)
                        }
                    }
            )
            Button(
                attrs = MainButtonStyle.toModifier()
                    .height(40.px)
                    .border(width = 0.px)
                    .borderRadius(r = 5.px)
                    .backgroundColor(Theme.Primary.rgb)
                    .color(Colors.White)
                    .cursor(Cursor.Pointer)
                    .toAttrs()
            ) {
                Text("Sign In")
            }
        }

    }
}
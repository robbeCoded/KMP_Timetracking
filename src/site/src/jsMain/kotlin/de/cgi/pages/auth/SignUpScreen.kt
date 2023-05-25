package de.cgi.pages.auth

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.auth.SignUpState
import de.cgi.common.auth.SignUpViewModel
import de.cgi.components.styles.MainButtonStyle
import de.cgi.components.styles.Theme
import de.cgi.components.widgets.AuthContainerStyle
import de.cgi.components.widgets.InputFieldStyle
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page(routeOverride = "signup")
@Composable
fun SignUpScreen() {
    val di = localDI()
    val viewModel: SignUpViewModel by di.instance()
    val signUpState by viewModel.signUpState.collectAsState()
    val ctx = rememberPageContext()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SignUpForm(
            onSignUpClick = viewModel::signUp,
            onSignUpNameChanged = viewModel::signUpNameChanged,
            onSignUpPasswordChanged = viewModel::signUpPasswordChanged,
            onSignUpEmailChanged = viewModel::signUpEmailChanged
        )
        Button(onClick = { ctx.router.navigateTo("/auth/signin") }) {
            Text("Sign in")
        }
    }

    when(signUpState) {
        is SignUpState.Success -> { ctx.router.navigateTo("/auth/signin") }
        is SignUpState.Loading -> { }
        is SignUpState.Failure -> { }
        is SignUpState.Error -> { }
        else -> {}
    }


}


@Composable
fun SignUpForm(
    onSignUpClick: () -> Unit,
    onSignUpNameChanged: (String) -> Unit,
    onSignUpEmailChanged: (String) -> Unit,
    onSignUpPasswordChanged: (String) -> Unit,
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Form(attrs = listOf(AuthContainerStyle).toAttrs {
        onSubmit { evt ->
            evt.preventDefault()
            onSignUpClick()
        }
    }) {
        Column(Modifier.fillMaxSize()) {
            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("Name")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        placeholder("Full Name")
                        name("name")
                        onChange {
                            name = it.value
                            onSignUpNameChanged(it.value)
                        }
                    }
            )

            Label(
                attrs = Modifier
                    .classNames("form-label")
                    .toAttrs(),
            ) {
                Text("E-Mail")
            }
            Input(
                InputType.Text,
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        placeholder("E-Mail Address")
                        name("email")
                        onChange {
                            email = it.value
                            onSignUpEmailChanged(it.value)
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
                attrs = listOf(InputFieldStyle)
                    .toAttrs {
                        placeholder("Password")
                        name("password")
                        onChange {
                            password = it.value
                            onSignUpPasswordChanged(it.value)
                        }
                    }
            )
            org.jetbrains.compose.web.dom.Button(
                attrs = MainButtonStyle.toModifier()
                    .height(40.px)
                    .border(width = 0.px)
                    .borderRadius(r = 5.px)
                    .backgroundColor(Theme.Primary.rgb)
                    .color(Colors.White)
                    .cursor(Cursor.Pointer)
                    .toAttrs()
            ) {
                Text("Sign Up")
            }
        }

    }
}
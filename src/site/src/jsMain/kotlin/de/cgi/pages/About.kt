package de.cgi.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.navigation.Link
import de.cgi.common.auth.SignUpViewModel
import de.cgi.components.layouts.PageLayout
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun AboutPage() {
    PageLayout("ABOUT") {
        Text("This is a skeleton app used to showcase a basic site made using Kobweb")
        P()
        Link("/", "Go Home")
    }
}
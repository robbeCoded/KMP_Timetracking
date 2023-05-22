package de.cgi.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import de.cgi.common.UserRepository
import de.cgi.components.layouts.PageLayout
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page
@Composable
fun HomePage() {
    val di = localDI()
    val userRepository: UserRepository by di.instance()
    val ctx = rememberPageContext()
    PageLayout("Home") {
        if (userRepository.getUserId().isBlank()){
            ctx.router.navigateTo("/auth/signin")
        } else {
            ctx.router.navigateTo("/timeentry/list")
        }
    }
}
package de.cgi.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.*
import com.varabyte.kobweb.silk.components.style.*
import de.cgi.common.UserRepository
import de.cgi.components.styles.*
import de.cgi.components.util.Constants
import de.cgi.components.util.Res
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.compose.localDI
import org.kodein.di.instance

val NavHeaderStyle by ComponentStyle.base {
    Modifier
        .fillMaxHeight()
        .width(15.percent)
        // Intentionally invert the header colors from the rest of the page
        .backgroundColor(Theme.LightGrey.rgb)
}

val NavItemStyle by ComponentStyle {
    base { Modifier.margin(leftRight = 15.px, topBottom = 5.px) }

    link { Modifier.color(Theme.Black.rgb) }
}

val NavButtonVariant by NavItemStyle.addVariant {
    base { Modifier.padding(0.px).borderRadius(50.percent) }
}


@Composable
fun NavMenu(
    onPage: String,
    pageContext: PageContext
) {
    val di = localDI()
    val userRepository: UserRepository by di.instance()

    val name = userRepository.getUserName()
    Box(NavHeaderStyle.toModifier()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(8.px)
                    .height(30.percent)
                    .fillMaxWidth()
                    .backgroundColor(Theme.DarkGrey.rgb),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                P(Heading1
                    .toModifier()
                    .color(Theme.CgiRed.rgb)
                    .toAttrs()){
                    Text("CGI")
                }

                Image(
                    modifier = Modifier
                        .height(80.px)
                        .width(80.px)
                        .borderRadius(50.percent)
                        .objectFit(ObjectFit.Cover),
                    src = Res.Image.noProfile,
                    desc = "Profile Picture"
                )

                P(Heading3
                    .toModifier()
                    .toAttrs()){
                    Text(name)
                }
            }
            Column(modifier = Modifier.fillMaxSize()) {
                MenuItem(
                    link = Constants.Navigation.calender.first,
                    text = Constants.Navigation.calender.second,
                    selected = Constants.Navigation.calender.second == onPage,
                    pageContext = pageContext
                )
                MenuItem(
                    link = Constants.Navigation.timeEntry.first,
                    text = Constants.Navigation.timeEntry.second,
                    selected = Constants.Navigation.timeEntry.second == onPage,
                    pageContext = pageContext
                )
                MenuItem(
                    link = Constants.Navigation.project.first,
                    text = Constants.Navigation.project.second,
                    selected = Constants.Navigation.project.second == onPage,
                    pageContext = pageContext
                )
                MenuItem(
                    link = Constants.Navigation.dashboard.first,
                    text = Constants.Navigation.dashboard.second,
                    selected = Constants.Navigation.dashboard.second == onPage,
                    pageContext = pageContext
                )
                MenuItem(
                    link = Constants.Navigation.settings.first,
                    text = Constants.Navigation.settings.second,
                    selected = Constants.Navigation.settings.second == onPage,
                    pageContext = pageContext
                )
                MenuItem(
                    link = Constants.Navigation.account.first,
                    text = Constants.Navigation.account.second,
                    selected = Constants.Navigation.account.second == onPage,
                    pageContext = pageContext
                )

            }
        }
    }
}

@Composable
fun MenuItem(
    link: String,
    text: String,
    selected: Boolean,
    pageContext: PageContext
) {
    val color = if (selected) {
        Theme.ActionSecondary.rgb
    } else {
        Theme.LightGrey.rgb
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.percent)
            .backgroundColor(color)
            .onClick { pageContext.router.navigateTo(link) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalSpacer(8)
        when(text) {
            "Calender" -> FaCalendarCheck()
            "Time Tracking" -> FaClock()
            "Project" -> FaFile()
            "Dashboard" -> FaChartLine()
            "Settings" -> FaGear()
            "Account" -> FaUser()
        }
        HorizontalSpacer(8)
        Div(Heading3.toModifier().toAttrs()){
            Text(text)
        }

    }
}
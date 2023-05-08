package de.cgi.android.navigation

abstract class AppRoutes(val baseRoute: String) {
    open val route: String = baseRoute
}
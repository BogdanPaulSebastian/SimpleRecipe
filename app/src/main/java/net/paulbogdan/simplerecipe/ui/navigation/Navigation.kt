package net.paulbogdan.simplerecipe.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import net.paulbogdan.simplerecipe.R

sealed class NavigationItem(
    val route: String,
    @StringRes val title: Int = 0,
    @DrawableRes val icon: Int = 0
) {

    //Main Screen
    object MainScreen : NavigationItem("main")
    object SplashScreen : NavigationItem("main/splash")

    //Main Bottom Navigation
    object HomeScreen : NavigationItem("main/home", icon = R.drawable.ic_home)
    object FavoritesScreen : NavigationItem("main/favorites", icon = R.drawable.ic_heart)
    object AccountScreen : NavigationItem("main/account", icon = R.drawable.ic_settings)

    //WebView
    object WebScreen : NavigationItem("main/web")

}

object ArgKeys {
    const val RECIPE_URL_KEY = "recipeUrl"
}

val mainRoutes = arrayOf(
    NavigationItem.FavoritesScreen.route,
    NavigationItem.HomeScreen.route,
    NavigationItem.AccountScreen.route
)

val DropDownMenuItems = listOf(
    "Open in Browser", "Disclaimer"
)

val bottomBarItems = arrayOf(
    NavigationItem.FavoritesScreen,
    NavigationItem.HomeScreen,
    NavigationItem.AccountScreen
)
package net.paulbogdan.simplerecipe.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.ui.bottomsheets.SimpleThemeMode
import net.paulbogdan.simplerecipe.ui.screens.MainScreen
import net.paulbogdan.simplerecipe.ui.screens.SplashScreen
import net.paulbogdan.simplerecipe.viewModel.AdViewModel
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalGlideComposeApi
@ExperimentalAnimationApi
@Composable
fun RootNavigation(
    navController: NavHostController,
    startDestinationScreen: NavigationItem,
    recipeViewModel: RecipeViewModel,
    adViewModel: AdViewModel,
    userPrefs : UserPrefs,
    onThemeModeChanged: (simpleThemeMode: SimpleThemeMode) -> Unit
) {
    val context = LocalContext.current

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestinationScreen.route
    ) {

        //Main Screen
        composable(
            route = NavigationItem.MainScreen.route
        ) {
            BackHandler(true) {
                (context as Activity).moveTaskToBack(true)
            }
            MainScreen(
                rootNavController = navController,
                recipeViewModel = recipeViewModel,
                adViewModel = adViewModel,
                userPrefs = userPrefs,
                onThemeModeChanged = onThemeModeChanged
            )
        }
        
        composable(
            route = NavigationItem.SplashScreen.route 
        ) {
            SplashScreen(navController = navController, userPrefs = userPrefs)
        }
    }
}
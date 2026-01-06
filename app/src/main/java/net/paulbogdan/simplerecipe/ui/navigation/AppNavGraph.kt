package net.paulbogdan.simplerecipe.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.ui.screens.*
import net.paulbogdan.simplerecipe.ui.theme.enterTransition
import net.paulbogdan.simplerecipe.ui.theme.exitTransition
import net.paulbogdan.simplerecipe.viewModel.AdViewModel
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

@ExperimentalGlideComposeApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun AppNavGraph(
    navController: NavHostController,
    rootNavController: NavController,
    recipeViewModel: RecipeViewModel,
    adViewModel: AdViewModel,
    scaffoldState: ScaffoldState,
    userPrefs: UserPrefs
) {

    val slideDirectionLeft = AnimatedContentScope.SlideDirection.Left
    val slideDirectionRight = AnimatedContentScope.SlideDirection.Right

    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationItem.HomeScreen.route
    ) {

        composable(
            route = NavigationItem.HomeScreen.route
        ) {
            SearchScreen(recipeViewModel, adViewModel, navController, userPrefs)
        }

        composable(
            route = NavigationItem.FavoritesScreen.route,
            enterTransition = enterTransition(slideDirectionRight),
            exitTransition = exitTransition(slideDirectionLeft)
        ) {
            FavoritesScreen(recipeViewModel, userPrefs, navController)
        }

        composable(
            route = NavigationItem.AccountScreen.route,
            enterTransition = enterTransition(slideDirectionLeft),
            exitTransition = exitTransition(slideDirectionRight)
        ) {
            AccountScreen(recipeViewModel)
        }

        composable(
            route = NavigationItem.WebScreen.route,
        ) {
            WebViewScreen(
                recipeViewModel = recipeViewModel,
                userPrefs = userPrefs,
                navController = navController
            )
        }
    }
}
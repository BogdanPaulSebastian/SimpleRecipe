package net.paulbogdan.simplerecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.ui.navigation.NavigationItem
import net.paulbogdan.simplerecipe.ui.navigation.RootNavigation
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.viewModel.AdViewModel
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel
import javax.inject.Inject

@ExperimentalGlideComposeApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPrefs: UserPrefs

    private val recipeViewModel: RecipeViewModel by viewModels()
    private val adViewModel: AdViewModel by viewModels()
    private lateinit var rootNavController: NavHostController


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adViewModel.loadAd()

        setContent {
            rootNavController = rememberAnimatedNavController()

            val startDestination = NavigationItem.SplashScreen
            val (selectedThemeMode, setSelectedThemeMode) = remember {
                mutableStateOf(userPrefs.getSimpleThemeMode())
            }

            SimpleTheme(
                darkTheme = when (selectedThemeMode.id) {
                    1 -> false
                    2 -> true
                    else -> isSystemInDarkTheme()
                }
            ) {
                RootNavigation(
                    navController = rootNavController,
                    startDestinationScreen = startDestination,
                    recipeViewModel = recipeViewModel,
                    adViewModel = adViewModel,
                    userPrefs = userPrefs,
                    onThemeModeChanged = { setSelectedThemeMode.invoke(it) }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recipeViewModel.onDestroy()
    }
}
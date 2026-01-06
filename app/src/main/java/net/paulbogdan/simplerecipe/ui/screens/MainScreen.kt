package net.paulbogdan.simplerecipe.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.ui.GlobalBottomSheet
import net.paulbogdan.simplerecipe.ui.LocalSelectThemeSheet
import net.paulbogdan.simplerecipe.ui.LocalShowFilterSheet
import net.paulbogdan.simplerecipe.ui.bottomsheets.FilterBottomSheet
import net.paulbogdan.simplerecipe.ui.bottomsheets.SelectThemeBottomSheet
import net.paulbogdan.simplerecipe.ui.bottomsheets.SimpleThemeMode
import net.paulbogdan.simplerecipe.ui.navigation.AppNavGraph
import net.paulbogdan.simplerecipe.ui.navigation.BottomBar
import net.paulbogdan.simplerecipe.ui.navigation.mainRoutes
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.viewModel.AdViewModel
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

@ExperimentalComposeUiApi
@ExperimentalGlideComposeApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(
    rootNavController: NavController,
    recipeViewModel: RecipeViewModel,
    adViewModel: AdViewModel,
    userPrefs: UserPrefs,
    onThemeModeChanged: (simpleThemeMode: SimpleThemeMode) -> Unit
) {
    val navController = rememberAnimatedNavController()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val filterBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val context = LocalContext.current

    val themeModalSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val filterBottomSheetController = GlobalBottomSheet(
        show = {
            coroutineScope.launch { filterBottomSheetState.animateTo(ModalBottomSheetValue.Expanded) }
        },
        hide = {
            coroutineScope.launch { filterBottomSheetState.hide() }
        }
    )

    var themeBottomSheetController = GlobalBottomSheet(
        show = {
            coroutineScope.launch { themeModalSheetState.animateTo(ModalBottomSheetValue.Expanded) }
        },
        hide = {
            coroutineScope.launch { themeModalSheetState.hide() }
        }
    )

    BackHandler {
        if (filterBottomSheetState.isVisible) {
            coroutineScope.launch { filterBottomSheetState.hide() }
        } else {
            (context as Activity).moveTaskToBack(true)
        }
    }

    CompositionLocalProvider(
        LocalShowFilterSheet provides filterBottomSheetController,
        LocalSelectThemeSheet provides themeBottomSheetController
    ) {
        ModalBottomSheetLayout(
            sheetState = themeModalSheetState,
            sheetContent = {
                SelectThemeBottomSheet(
                    userPrefs = userPrefs,
                    onThemeChanged = onThemeModeChanged
                )
            },
            sheetBackgroundColor = SimpleTheme.colors.pageBackground,
            sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            scrimColor = SimpleTheme.colors.scrimColor.copy(0.3f),
            sheetElevation = 8.dp
        ) {
            ModalBottomSheetLayout(
                sheetContent = {
                    FilterBottomSheet(
                        recipeViewModel,
                        onSearchButtonClick = {
                            recipeViewModel.getRecipe(
                                query = recipeViewModel.recipeQuery,
                                healthFilters = recipeViewModel.filteredHealthLabelsList.toTypedArray(),
                                cuisineType = recipeViewModel.filteredCuisineList.toTypedArray()
                            )
                            coroutineScope.launch { filterBottomSheetState.hide() }
                        })
                },
                sheetState = filterBottomSheetState,
                sheetBackgroundColor = SimpleTheme.colors.pageBackground,
                sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                scrimColor = SimpleTheme.colors.scrimColor.copy(0.3f),
                sheetElevation = 8.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val isMainRoute = currentRoute in mainRoutes

                Scaffold(bottomBar = {
                    if (isMainRoute) {
                        BottomBar(navController = navController)
                    }
                }, backgroundColor = SimpleTheme.colors.pageBackground) {
                    Box(modifier = Modifier.padding(it)) {
                        AppNavGraph(
                            navController = navController,
                            rootNavController = rootNavController,
                            recipeViewModel = recipeViewModel,
                            adViewModel = adViewModel,
                            scaffoldState = scaffoldState,
                            userPrefs = userPrefs
                        )
                    }
                }
            }
        }
    }
}
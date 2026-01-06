package net.paulbogdan.simplerecipe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.ui.components.ConfirmationDialog
import net.paulbogdan.simplerecipe.ui.components.DisclaimerDialog
import net.paulbogdan.simplerecipe.ui.components.SimpleBrowserBar
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

@Composable
fun WebViewScreen(
    recipeViewModel: RecipeViewModel,
    userPrefs: UserPrefs,
    navController: NavController
) {

    val recipe = recipeViewModel.activeRecipe
    val state = rememberWebViewState(url = recipe.url)
    val uriHandler = LocalUriHandler.current

    val isConfirmationDialogShown = remember { mutableStateOf(false) }
    val isDisclaimerDialogShown = remember { mutableStateOf(false) }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    var isFavorite by remember { mutableStateOf(false) }
    if (recipeViewModel.favoriteRecipeList.contains(recipe)) isFavorite = true

    LaunchedEffect(key1 = true) {
        if (!userPrefs.seenDisclaimerOnce()) {
            isDisclaimerDialogShown.value = true
        }
    }

    Box(Modifier.fillMaxSize()) {

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { source, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    if (recipeViewModel.isBrowsing.value) {
                        recipeViewModel.isBrowsing.value = false
                        navController.navigate(recipeViewModel.savedRoute.value, navOptions {
                            popUpTo(recipeViewModel.savedRoute.value)
                        })
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        if (isConfirmationDialogShown.value) {
            ConfirmationDialog(
                onDismissRequest = { isConfirmationDialogShown.value = false },
                onConfirm = {
                    recipeViewModel.createFavoriteList(recipe)
                    isFavorite = !isFavorite
                    isConfirmationDialogShown.value = false
                },
                userPrefs = userPrefs
            )
        }

        if (isDisclaimerDialogShown.value) {
            DisclaimerDialog(
                onDismissRequest = { isDisclaimerDialogShown.value = false },
                onConfirm = {
                    userPrefs.setSeenDisclaimerOnce(true)
                    isDisclaimerDialogShown.value = false
                }
            )
        }

        Column(Modifier.background(SimpleTheme.colors.pageBackground)) {
            Spacer(modifier = Modifier.height(34.dp))
            WebView(state = state, onCreated = { it.settings.javaScriptEnabled = userPrefs.getJavaScripPreference() })
        }

        SimpleBrowserBar(
            recipe = recipe,
            onFavoriteButtonClick = {
                if (userPrefs.getShowConfirmationDialog()) {
                    if (recipeViewModel.favoriteRecipeList.contains(recipe)) {
                        isConfirmationDialogShown.value = true
                    } else {
                        recipeViewModel.createFavoriteList(recipe)
                        isFavorite = !isFavorite
                    }
                } else {
                    recipeViewModel.createFavoriteList(recipe)
                    isFavorite = !isFavorite
                }
            },
            openInBrowSerClick = {
                recipeViewModel.isBrowsing.value = true
                uriHandler.openUri(recipe.url)
            },
            openDisclaimerClick = {
                isDisclaimerDialogShown.value = true
            },
            isFavorite = isFavorite
        )
    }
}
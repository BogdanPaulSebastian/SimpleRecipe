package net.paulbogdan.simplerecipe.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.model.Recipe
import net.paulbogdan.simplerecipe.ui.components.ConfirmationDialog
import net.paulbogdan.simplerecipe.ui.components.RecipeDetailCard
import net.paulbogdan.simplerecipe.ui.components.RecipePreviewCard
import net.paulbogdan.simplerecipe.ui.components.SimpleSearchBar
import net.paulbogdan.simplerecipe.ui.navigation.NavigationItem
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

@ExperimentalGlideComposeApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun FavoritesScreen(
    recipeViewModel: RecipeViewModel,
    userPrefs: UserPrefs,
    navController: NavController,
) {

    val isDialogShown = remember { mutableStateOf(false) }
    val isConfirmationDialogShown = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val recipe: Recipe = recipeViewModel.activeRecipe

    val favoriteRecipeList = recipeViewModel.favoriteRecipeList.filter {
        it.label.contains(recipeViewModel.favoriteSearchQuery, ignoreCase = true)
    }
    val isSearching =
        if (recipeViewModel.favoriteSearchQuery.isEmpty()) remember { mutableStateOf(false) } else remember {
            mutableStateOf(true)
        }

    Scaffold(backgroundColor = SimpleTheme.colors.pageBackground) {

        Box(
            Modifier.fillMaxSize(),
        ) {
            if (favoriteRecipeList.isEmpty()) {
                if (isSearching.value) {
                    NoSearchedFavoritesFound(searchMoreClick = {
                        navController.popBackStack(NavigationItem.HomeScreen.route, false)
                        keyboardController?.hide()
                    })
                } else {
                    NoFavoritesSaved(userPrefs)
                }
            } else {

                if (isDialogShown.value) {
                    RecipeDetailCard(
                        recipe = recipe,
                        onDismissRequest = { isDialogShown.value = false },
                        onFavouriteClick = {
                            if (userPrefs.getShowConfirmationDialog()) {
                                isDialogShown.value = false
                                isConfirmationDialogShown.value = true
                            } else {
                                recipeViewModel.createFavoriteList(recipe)
                                isDialogShown.value = false
                            }
                        },
                        onGoToRecipeClick = {
                            isDialogShown.value = false
                            recipeViewModel.savedRoute.value = NavigationItem.FavoritesScreen.route
                            navController.navigate(NavigationItem.WebScreen.route)
                        },
                        recipeViewModel = recipeViewModel,
                    )
                }

                if (isConfirmationDialogShown.value) {
                    ConfirmationDialog(
                        onDismissRequest = { isConfirmationDialogShown.value = false },
                        onConfirm = {
                            recipeViewModel.createFavoriteList(recipe)
                            isConfirmationDialogShown.value = false
                        },
                        userPrefs = userPrefs
                    )
                }

                Spacer(modifier = Modifier.height(200.dp))
                LazyVerticalGrid(
                    modifier = Modifier.padding(
                        top = 80.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
                    columns = GridCells.Adaptive(150.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        favoriteRecipeList.forEach { result ->
                            favoriteRecipeList.firstOrNull {
                                it.label.contains(result.label, ignoreCase = true)
                            }?.let {
                                item {
                                    RecipePreviewCard(recipe = it, onRecipeClick = {
                                        recipeViewModel.activeRecipe = it
                                        isDialogShown.value = true
                                    })
                                }
                            }
                        }
                    })
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                SimpleSearchBar(
                    text = recipeViewModel.favoriteSearchQuery,
                    topText = stringResource(id = R.string.search_favorites),
                    bottomText = stringResource(id = R.string.filter_by_name),
                    onSearch = {
                        keyboardController?.hide()
                    },
                    onTextChanged = {
                        recipeViewModel.favoriteSearchQuery = it
                    },
                    onClearClick = {
                        recipeViewModel.favoriteSearchQuery = ""
                    }
                )
            }
        }
    }
}

@Composable
fun NoFavoritesSaved(userPrefs: UserPrefs) {

    val selectedThemeMode = remember {
        mutableStateOf(userPrefs.getSimpleThemeMode())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stringResource(id = R.string.no_recipes_saved),
                fontFamily = Georama,
                fontSize = 34.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = SimpleTheme.colors.almostBlack
            )

            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(
                    id = when (selectedThemeMode.value.id) {
                        1 -> R.drawable.simplerecipe_icon_notext
                        2 -> R.drawable.simplerecipe_icon_notext_dark
                        else -> if (isSystemInDarkTheme()) R.drawable.simplerecipe_icon_notext_dark else R.drawable.simplerecipe_icon_notext
                    }
                ),
                contentDescription = stringResource(id = R.string.dish),
                contentScale = ContentScale.FillWidth,
            )

            Text(
                modifier = Modifier.width(280.dp),
                text = stringResource(id = R.string.tap_heart),
                fontFamily = Georama,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = SimpleTheme.colors.almostBlack
            )
        }
    }
}

@Composable
fun NoSearchedFavoritesFound(searchMoreClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stringResource(id = R.string.no_recipes_found),
                fontFamily = Georama,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = SimpleTheme.colors.almostBlack
            )

            Text(
                modifier = Modifier
                    .width(280.dp)
                    .clickable { searchMoreClick() },
                text = "${stringResource(id = R.string.search_cta)} >",
                fontFamily = Georama,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = SimpleTheme.colors.green
            )
        }
    }
}
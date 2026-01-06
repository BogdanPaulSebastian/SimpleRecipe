package net.paulbogdan.simplerecipe.ui.screens

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.model.Recipe
import net.paulbogdan.simplerecipe.ui.LocalShowFilterSheet
import net.paulbogdan.simplerecipe.ui.components.BannerAd
import net.paulbogdan.simplerecipe.ui.components.RecipeDetailCard
import net.paulbogdan.simplerecipe.ui.components.RecipePreviewCard
import net.paulbogdan.simplerecipe.ui.components.SearchBarWithFilter
import net.paulbogdan.simplerecipe.ui.navigation.NavigationItem
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.viewModel.AdViewModel
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

@ExperimentalGlideComposeApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    recipeViewModel: RecipeViewModel,
    adViewModel: AdViewModel,
    navController: NavController,
    userPrefs: UserPrefs
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val searchResult = recipeViewModel.searchResult
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }
    val isDialogShown = remember { mutableStateOf(false) }
    val recipe: Recipe = recipeViewModel.activeRecipe
    val activity = LocalContext.current as Activity
    val isLoading = recipeViewModel.loadingStatus

    Scaffold(backgroundColor = SimpleTheme.colors.pageBackground) {

        Box(Modifier.fillMaxSize()) {
            if (searchResult.isEmpty()) NoRecipesLoaded(userPrefs) else {

                if (isDialogShown.value) {
                    RecipeDetailCard(
                        recipe = recipe,
                        onDismissRequest = { isDialogShown.value = false },
                        onFavouriteClick = { recipeViewModel.createFavoriteList(recipe) },
                        onGoToRecipeClick = {
                            isDialogShown.value = false
                            recipeViewModel.savedRoute.value = NavigationItem.HomeScreen.route
                            navController.navigate(NavigationItem.WebScreen.route)
                        },
                        recipeViewModel = recipeViewModel,
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
                        var counter = 1
                        searchResult.forEach { result ->
                            searchResult.firstOrNull {
                                it.label.equals(result.label, ignoreCase = true)
                            }?.let {
                                if (counter % 6 == 0) {
                                    counter++
                                    item {
                                        RecipePreviewCard(recipe = it, onRecipeClick = {
                                            recipeViewModel.activeRecipe = it
                                            isDialogShown.value = true
                                        })
                                    }
                                    item(span = span) { BannerAd() }
                                } else {
                                    counter++
                                    item {
                                        RecipePreviewCard(recipe = it, onRecipeClick = {
                                            recipeViewModel.activeRecipe = it
                                            isDialogShown.value = true
                                        })
                                    }
                                }
                            }
                        }
                        if (recipeViewModel.apiResponse.value?._links?.next?.href?.isNotEmpty() == true) {
                            item(span = span) {
                                Button(
                                    onClick = {
                                        adViewModel.showAd(activity)
                                        recipeViewModel.nextPage()
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = SimpleTheme.colors.green),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.load_more),
                                        fontFamily = Georama,
                                        fontSize = 18.sp,
                                        color = SimpleTheme.colors.white,
                                    )
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
                SearchBarWithFilter(
                    text = recipeViewModel.recipeQuery,
                    onTextChanged = { recipeViewModel.recipeQuery = it },
                    hasFilters = !(recipeViewModel.filteredCuisineList.isEmpty() && recipeViewModel.filteredHealthLabelsList.isEmpty()),
                    onSearch = {
                        recipeViewModel.getRecipe(
                            query = recipeViewModel.recipeQuery,
                            healthFilters = recipeViewModel.filteredHealthLabelsList.toTypedArray(),
                            cuisineType = recipeViewModel.filteredCuisineList.toTypedArray()
                        )
                        keyboardController?.hide()
                    },
                    onFilterClick = LocalShowFilterSheet.current.show ?: {}
                )
            }
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                        {/*Blocks all interactions*/ }
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = SimpleTheme.colors.green
                    )
                }
            }
        }
    }
}

@Composable
fun NoRecipesLoaded(userPrefs: UserPrefs) {

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

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp),
                painter = painterResource(
                    id = when (selectedThemeMode.value.id) {
                        1 -> R.drawable.simplerecipe_splash
                        2 -> R.drawable.simplerecipe_splash_dark
                        else -> if (isSystemInDarkTheme()) R.drawable.simplerecipe_splash_dark else R.drawable.simplerecipe_splash
                    }
                ),
                contentDescription = stringResource(id = R.string.dish),
                contentScale = ContentScale.FillWidth
            )

        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.edamam_badge_transparent),
                contentDescription = stringResource(id = R.string.powered_by),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(300.dp)
                    .padding(bottom = 32.dp),
            )
        }
    }
}

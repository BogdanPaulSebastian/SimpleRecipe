package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.model.Ingredient
import net.paulbogdan.simplerecipe.model.Recipe
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

val ingredient = Ingredient(
    text = "potato chicken smash or pass",
    quantity = 0.25,
    measure = "Icter",
    food = "potato",
    weight = 30.00,
    foodId = "food",
)

val recipeMockup = Recipe(
    label = "Cartofi Gratinati",
    image = "",
    uri = "",
    url = "",
    shareAs = "",
    calories = 2.0,
    yield = 4.0,
    healthLabels = arrayListOf<String>(
        "Vegetarian",
        "Pescatarian",
        "Paleo",
        "Dairy-Free",
        "Gluten-Free",
        "Wheat-Free",
        "Egg-Free",
        "Peanut-Free",
        "Tree-Nut-Free",
        "Soy-Free",
        "Fish-Free",
        "Shellfish-Free",
        "Pork-Free",
        "Red-Meat-Free",
        "Crustacean-Free",
        "Celery-Free",
        "Mustard-Free",
        "Sesame-Free",
        "Lupine-Free",
        "Mollusk-Free",
        "Alcohol-Free",
        "Kosher"
    ),
    ingredients = arrayListOf(
        ingredient, ingredient, ingredient, ingredient, ingredient, ingredient
    )
)

@ExperimentalGlideComposeApi
@Composable
fun RecipePreviewCard(
    recipe: Recipe,
    onRecipeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = SimpleTheme.colors.white, shape = RoundedCornerShape(20.dp))
            .aspectRatio(1f)
            .clickable { onRecipeClick() },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = SimpleTheme.colors.green)
        GlideImage(
            model = recipe.image,
            contentDescription = recipe.label,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = SimpleTheme.colors.almostBlack.copy(0.7f),
                    shape = RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp,
                        topStart = 5.dp,
                        topEnd = 5.dp
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = recipe.label,
                fontFamily = FontFamily(Font(R.font.georama_regular)),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = SimpleTheme.colors.white,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@ExperimentalGlideComposeApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun RecipeDetailCard(
    recipe: Recipe,
    onDismissRequest: () -> Unit,
    onFavouriteClick: () -> Unit,
    onGoToRecipeClick: () -> Unit,
    recipeViewModel: RecipeViewModel
) {
    var isFavorite by remember { mutableStateOf(false) }

    if (recipeViewModel.favoriteRecipeList.contains(recipe)) isFavorite = true

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {

        Card(
            shape = RoundedCornerShape(15.dp),
            backgroundColor = SimpleTheme.colors.pageBackground
        ) {
            AnimatedVisibility(visible = true) {
                Column(Modifier.fillMaxWidth()) {
                    Box {
                        GlideImage(
                            model = recipe.image,
                            contentDescription = recipe.label,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.7f)
                        )
                        Box(
                            Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                Modifier
                                    .align(Alignment.TopEnd)
                                    .border(
                                        0.1.dp,
                                        color = SimpleTheme.colors.almostBlack,
                                        shape = RoundedCornerShape(100.dp)
                                    )
                                    .background(
                                        color = SimpleTheme.colors.white.copy(0.5f),
                                        shape = RoundedCornerShape(100.dp)
                                    )
                                    .size(24.dp)
                            ) {
                                IconButton(onClick = onDismissRequest) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_baseline_close_24),
                                        contentDescription = stringResource(id = R.string.close)
                                    )
                                }
                            }
                        }
                    }
                    Divider(
                        modifier = Modifier
                            .border(width = 10.dp, color = SimpleTheme.colors.green)
                            .height(3.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .widthIn(min = 1.dp, max = 260.dp)
                            ) {
                                Text(
                                    text = recipe.label,
                                    fontFamily = FontFamily(Font(R.font.georama_regular)),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = SimpleTheme.colors.almostBlack,
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                )
                            }
                            Box(modifier = Modifier) {
                                FavoriteButton(
                                    modifier = Modifier.size(30.dp),
                                    uncheckedColor = SimpleTheme.colors.lightGray,
                                    color = SimpleTheme.colors.green,
                                    onFavoriteToggle = {
                                        isFavorite = !isFavorite
                                        onFavouriteClick()
                                    },
                                    isFavorite = isFavorite
                                )
                            }
                        }
                        SimpleDivider()
                        Box(modifier = Modifier.fillMaxWidth()) {
                            ExpandableCardWithTags(recipe = recipe)
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.ingredients),
                                fontFamily = FontFamily(Font(R.font.georama_regular)),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = SimpleTheme.colors.almostBlack,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                                modifier = Modifier
                            )
                            if (recipe.ingredients.size > 17)
                                Text(
                                    text = stringResource(id = R.string.scroll_to_see_more),
                                    fontFamily = FontFamily(Font(R.font.georama_regular)),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = SimpleTheme.colors.deepGray,
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                )

                        }

                        LazyColumn(Modifier.fillMaxWidth()) {
                            item {
                                recipe.ingredients.forEach { ingredient ->
                                    Text(
                                        fontFamily = FontFamily(Font(R.font.georama_regular)),
                                        text = "• ${ingredient.text}",
                                        textAlign = TextAlign.Start,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Normal,
                                        maxLines = 15,
                                        overflow = TextOverflow.Ellipsis,
                                        color = SimpleTheme.colors.almostBlack
                                    )
                                }
                            }
                            item {
                                Box(
                                    Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(
                                        onClick = {
                                            onGoToRecipeClick()
                                        },
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = SimpleTheme.colors.white),
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.go_to_recipe),
                                            fontFamily = FontFamily(Font(R.font.georama_regular)),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SimpleTheme.colors.green,
                                            textAlign = TextAlign.Start,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@ExperimentalGlideComposeApi
@Preview
@Composable
fun CardPreveiw() {
    RecipePreviewCard(recipeMockup, {})
}
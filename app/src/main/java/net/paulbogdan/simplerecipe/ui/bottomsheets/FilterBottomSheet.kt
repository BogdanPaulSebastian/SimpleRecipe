package net.paulbogdan.simplerecipe.ui.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.extensions.capitalize
import net.paulbogdan.simplerecipe.model.allergies
import net.paulbogdan.simplerecipe.model.cuisineType
import net.paulbogdan.simplerecipe.model.dietTypes
import net.paulbogdan.simplerecipe.model.misc
import net.paulbogdan.simplerecipe.ui.components.DragBar
import net.paulbogdan.simplerecipe.ui.components.FilterDivider
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

@Composable
fun FilterBottomSheet(
    recipeViewModel: RecipeViewModel,
    onSearchButtonClick: () -> Unit,
) {

    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }
    val filteredHealthLabelsList = recipeViewModel.filteredHealthLabelsList
    val filteredCuisineList = recipeViewModel.filteredCuisineList

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = SimpleTheme.colors.deepGray.copy(0.05f))
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
    ) {
        Box {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {

                item(span = span) {
                    Box(contentAlignment = Alignment.Center) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DragBar(SimpleTheme.colors.almostBlack)
                            Text(
                                text = stringResource(id = R.string.filters),
                                fontFamily = FontFamily(Font(R.font.georama_regular)),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = SimpleTheme.colors.almostBlack,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                            )
                        }
                    }
                }

                item(span = span) {
                    FilterDivider(title = stringResource(id = R.string.diet))
                }
                dietTypes.forEach { diet ->
                    item {
                        SelectableFilterItem(
                            filterLabel = diet,
                            onClick = { recipeViewModel.createHealthFilterList(diet) },
                            filteredHealthLabelsList
                        )
                    }
                }

                item(span = span) {
                    FilterDivider(title = stringResource(id = R.string.allergies))
                }
                allergies.forEach { allergy ->
                    item {
                        SelectableFilterItem(
                            filterLabel = allergy,
                            onClick = { recipeViewModel.createHealthFilterList(allergy) },
                            filteredHealthLabelsList
                        )
                    }
                }

                item(span = span) {
                    FilterDivider(title = stringResource(id = R.string.cuisine))
                }
                cuisineType.forEach { cuisine ->
                    item {
                        SelectableFilterItem(
                            filterLabel = cuisine,
                            onClick = { recipeViewModel.createCuisineFilterList(cuisine) },
                            filteredCuisineList
                        )
                    }
                }

                item(span = span) {
                    FilterDivider(title = stringResource(id = R.string.misc))
                }
                misc.forEach { misc ->
                    item {
                        SelectableFilterItem(
                            filterLabel = misc,
                            onClick = { recipeViewModel.createHealthFilterList(misc) },
                            filteredHealthLabelsList
                        )
                    }
                }

                item(span = span) {
                    Spacer(modifier = Modifier.height(55.dp))
                }

            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            ) {
                BottomSheetButton(onSearchButtonClick = onSearchButtonClick)
            }
        }

    }
}

@Composable
fun SelectableFilterItem(
    filterLabel: String,
    onClick: () -> Unit,
    filteredList: List<String>
) {
    var checked by remember { mutableStateOf(false) }

    checked = filteredList.contains(filterLabel)

    Box(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = if (checked) SimpleTheme.colors.green else SimpleTheme.colors.lightGray,
                RoundedCornerShape(20.dp)
            )
            .background(
                color = if (checked) SimpleTheme.colors.green else SimpleTheme.colors.white,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(4.dp)
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                checked = !checked
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = filterLabel.capitalize(),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            color = if (checked) SimpleTheme.colors.white else SimpleTheme.colors.almostBlack,
            fontFamily = Georama,
        )
    }
}

@Composable
fun BottomSheetButton(
    onSearchButtonClick: () -> Unit
) {

    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = onSearchButtonClick,
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = SimpleTheme.colors.white),
            modifier = Modifier
                .width(160.dp)
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.apply_and_search),
                fontFamily = FontFamily(Font(R.font.georama_regular)),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = SimpleTheme.colors.green,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun FilterPreview() {
    SelectableFilterItem(
        filterLabel = "Vegetarian",
        onClick = { /*TODO*/ },
        filteredList = listOf()
    )
}
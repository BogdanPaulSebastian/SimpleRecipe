package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
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
import net.paulbogdan.simplerecipe.model.Recipe
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ExpandableCardWithTags(recipe: Recipe) {

    var expandableState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandableState) 180f else 0f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = SimpleTheme.colors.white.copy(alpha = 0f),
        elevation = 0.dp,
        onClick = { expandableState = !expandableState },
        indication = null
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                if (!expandableState) {
                    
                    recipe.healthLabels.toList().sortedBy { it.length }.take(4).forEach { label -> recipe.healthLabels.firstOrNull { recipe.healthLabels.equals(label) }
                        .let { HealthLabel(label = label) }
                    }

                } else {
                    Text(
                        modifier = Modifier.weight(6f),
                        fontFamily = FontFamily(Font(R.font.georama_regular)),
                        text = stringResource(id = R.string.health_tags),
                        textAlign = TextAlign.Start,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = SimpleTheme.colors.almostBlack
                    )
                }

                IconButton(onClick = { expandableState = !expandableState }) {
                    Icon(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .rotate(rotationState),
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.dropdown_arrow),
                        tint = SimpleTheme.colors.almostBlack
                    )
                }
            }
            if (expandableState) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(150.dp)
                ) {
                    items(count = recipe.healthLabels.size) { labelPos ->
                        HealthLabel(
                            label = recipe.healthLabels[labelPos]
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ExpandableCardWithTitle(recipe: Recipe) {

    var expandableState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandableState) 180f else 0f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = SimpleTheme.colors.white.copy(alpha = 0f),
        elevation = 0.dp,
        onClick = { expandableState = !expandableState }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = stringResource(id = R.string.ingredients),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(onClick = { expandableState = !expandableState }) {
                    Icon(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .weight(1f)
                            .rotate(rotationState),
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.dropdown_arrow)
                    )
                }
            }
            if (expandableState) {
                Column(Modifier.fillMaxWidth()) {
                    recipe.ingredients.forEach { ingredient ->
                        Text(
                            text = ingredient.text,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun DropdownPreviewTags() {
    ExpandableCardWithTags(recipe = recipeMockup)
}
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun DropdownPreviewTitle() {
    ExpandableCardWithTitle(recipe = recipeMockup)
}

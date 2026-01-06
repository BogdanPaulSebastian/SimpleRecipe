package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.extensions.semiBorder
import net.paulbogdan.simplerecipe.model.Recipe
import net.paulbogdan.simplerecipe.ui.navigation.DropDownMenuItems
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun SimpleBrowserBar(
    recipe: Recipe,
    onFavoriteButtonClick: () -> Unit,
    openInBrowSerClick: () -> Unit,
    openDisclaimerClick: () -> Unit,
    isFavorite: Boolean
) {

    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(
                color = SimpleTheme.colors.pageBackground,
                shape = RoundedCornerShape(20.dp)
            )
            .semiBorder(1.dp, color = SimpleTheme.colors.lightGray, cornerRadiusDp = 20.dp, false)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(0.8f),
                    text = recipe.label,
                    fontFamily = Georama,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    color = SimpleTheme.colors.almostBlack
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FavoriteButton(
                        modifier = Modifier,
                        uncheckedColor = SimpleTheme.colors.lightGray,
                        color = SimpleTheme.colors.green,
                        onFavoriteToggle = {
                            onFavoriteButtonClick()
                        },
                        isFavorite = isFavorite
                    )
                    IconButton(onClick = { expanded.value = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.dropdown_menu),
                            tint = SimpleTheme.colors.green
                        )
                    }
                }
                MaterialTheme(
                    colors = MaterialTheme.colors.copy(surface = SimpleTheme.colors.pageBackground),
                    shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(20))
                ) {
                    DropdownMenu(
                        expanded = expanded.value,
                        offset = DpOffset(((-40).dp), ((0).dp)),
                        onDismissRequest = { expanded.value = false },
                        properties = PopupProperties(),
                    ) {
                        DropDownMenuItems.forEach {
                            DropdownMenuItem(onClick = {
                                if (it == DropDownMenuItems.first()) {
                                    expanded.value = false
                                    openInBrowSerClick()
                                } else {
                                    expanded.value = false
                                    openDisclaimerClick()
                                }
                            })
                            {
                                Text(
                                    text = it,
                                    color = SimpleTheme.colors.almostBlack
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
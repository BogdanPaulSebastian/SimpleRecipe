package net.paulbogdan.simplerecipe.ui.bottomsheets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.ui.components.DragBar
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun SelectThemeBottomSheet(
    userPrefs: UserPrefs,
    onThemeChanged: (SimpleThemeMode: SimpleThemeMode) -> Unit
) {
    val (simpleThemeModes, setSimpleThemeModes) = remember {
        mutableStateOf(SimpleThemeMode.getSimpleThemeMode(userPrefs.getSimpleThemeMode().id))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = SimpleTheme.colors.deepGray.copy(0.05f))
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
    ) {
        DragBar(SimpleTheme.colors.almostBlack,)
        Text(
            text = stringResource(id = R.string.select_theme),
            fontFamily = FontFamily(Font(R.font.georama_regular)),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SimpleTheme.colors.almostBlack,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            itemsIndexed(simpleThemeModes) { _, simpleThemeMode ->
                ThemeItem(
                    id = simpleThemeMode.id,
                    img = simpleThemeMode.img,
                    text = simpleThemeMode.text,
                    checked = simpleThemeMode.checked,
                    onClick = { id ->
                        setSimpleThemeModes(simpleThemeModes.map {
                            if (it.id == id) {
                                val newMode = it.copy(checked = true)
                                userPrefs.setSimpleThemeMode(newMode)
                                onThemeChanged(newMode)
                                return@map newMode
                            } else {
                                return@map it.copy(checked = false)
                            }
                        })
                    }
                )
            }
        }
    }
}

@Composable
fun ThemeItem(
    id: Int,
    @DrawableRes img: Int,
    @StringRes text: Int,
    checked: Boolean = false,
    onClick: (id: Int) -> Unit = {}
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember {
                MutableInteractionSource()
            },
            onClick = { onClick(id) }
        ),
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = "",
            modifier = Modifier.padding(bottom = 8.dp)
                .size(100.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = stringResource(id = text),
            fontFamily = Georama,
            fontSize = 16.sp,
            color = SimpleTheme.colors.almostBlack
        )

        Image(
            modifier = Modifier
                .padding(bottom = 16.dp, top = 8.dp)
                .size(25.dp),
            painter = if (checked) {
                painterResource(id = R.drawable.ic_checked)
            } else {
                painterResource(id = R.drawable.ic_unchecked)
            },
            contentDescription = "",
            colorFilter = ColorFilter.tint(SimpleTheme.colors.green)
        )
    }
}

data class SimpleThemeMode(
    val id: Int,
    val img: Int,
    val text: Int,
    val checked: Boolean = false
) {
    companion object {
        fun getSimpleThemeMode(id: Int): List<SimpleThemeMode> {
            return listOf(
                SimpleThemeMode(
                    id = 1,
                    img = R.drawable.vanilla,
                    text = R.string.light,
                    checked = id == 1
                ),
                SimpleThemeMode(
                    id = 2,
                    img = R.drawable.chocholate,
                    text = R.string.dark,
                    checked = id == 2
                ),
                SimpleThemeMode(
                    id = 3,
                    img = R.drawable.system,
                    text = R.string.system,
                    checked = id == 3
                ),
            )
        }
    }
}
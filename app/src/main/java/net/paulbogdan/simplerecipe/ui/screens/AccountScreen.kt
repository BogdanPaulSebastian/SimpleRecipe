package net.paulbogdan.simplerecipe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.ui.LocalSelectThemeSheet
import net.paulbogdan.simplerecipe.ui.components.NavigationListItem
import net.paulbogdan.simplerecipe.ui.components.ToggleListItem
import net.paulbogdan.simplerecipe.ui.settings.rateUs
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.viewModel.RecipeViewModel

@Composable
fun AccountScreen(
    recipeViewModel: RecipeViewModel,
) {

    val context = LocalContext.current
    var isUnfavoriteChecked by remember { mutableStateOf(recipeViewModel.userPrefs.getShowConfirmationDialog()) }
    var isJavaScriptChecked by remember { mutableStateOf(recipeViewModel.userPrefs.getJavaScripPreference()) }

    Box(
        Modifier
            .fillMaxSize()
            .background(SimpleTheme.colors.pageBackground),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Text(
                        text = stringResource(id = R.string.settings),
                        fontFamily = Georama,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = SimpleTheme.colors.almostBlack
                    )
                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = SimpleTheme.colors.deepGray
                    )
                }
                item {
                    ToggleListItem(
                        iconResId = R.drawable.ic_speaker_notes,
                        titleResId = R.string.confirm_delete,
                        isChecked = isUnfavoriteChecked,
                        onClick = {
                            isUnfavoriteChecked = !isUnfavoriteChecked
                            recipeViewModel.userPrefs.setShowConfirmationDialog(isUnfavoriteChecked)
                        }
                    )
                }

                item {
                    ToggleListItem(
                        iconResId = R.drawable.ic_javascript,
                        titleResId = R.string.enable_javascript,
                        captionResId = R.string.javascript_caption,
                        isChecked = isJavaScriptChecked,
                        onClick = {
                            isJavaScriptChecked = !isJavaScriptChecked
                            recipeViewModel.userPrefs.setJavaScriptPreference(isJavaScriptChecked)
                        }
                    )
                }

                item {
                    NavigationListItem(
                        iconResId = R.drawable.ic_theme,
                        titleResId = R.string.select_theme,
                        onClick = LocalSelectThemeSheet.current.show ?: {}
                    )
                }

                item {
                    NavigationListItem(
                        iconResId = R.drawable.ic_heart,
                        titleResId = R.string.rate_us_on_google_play,
                        onClick = { rateUs(context) }
                    )
                }
            }
            Text(
                text = "${stringResource(id = R.string.app_version)} v1.0",
                fontFamily = Georama,
                color = SimpleTheme.colors.deepGray,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


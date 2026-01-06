package net.paulbogdan.simplerecipe.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.ui.navigation.NavigationItem
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun SplashScreen(
    navController: NavController,
    userPrefs: UserPrefs
) {
    val selectedThemeMode = remember {
        mutableStateOf(userPrefs.getSimpleThemeMode())
    }

    val scale = remember { androidx.compose.animation.core.Animatable(0.0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(500)
        navController.navigate(NavigationItem.MainScreen.route) {
            popUpTo(NavigationItem.SplashScreen.route) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SimpleTheme.colors.pageBackground),
    ) {
        Image(
            painter = painterResource(
                id = when (selectedThemeMode.value.id) {
                    1 -> R.drawable.simplerecipe_icon_notext
                    2 -> R.drawable.simplerecipe_icon_notext_dark
                    else -> if (isSystemInDarkTheme()) R.drawable.simplerecipe_icon_notext_dark else R.drawable.simplerecipe_icon_notext
                }),
            contentDescription = null,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .scale(scale.value)
        )
    }
}
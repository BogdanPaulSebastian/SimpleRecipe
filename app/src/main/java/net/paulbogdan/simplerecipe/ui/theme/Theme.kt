package net.paulbogdan.simplerecipe.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = SimpleColors(
    pageBackground = PageBackgroundDark,
    almostBlack = AlmostBlackDark,
    white = WhiteDark,
    green = GreenDark,
    deepGray = DeepGreyDark,
    lightGray = LightGreyDark,
    divider = LightGreyDark.copy(0.3f),
    textColor = SearchText,
    captionColor = SearchCaption,
    scrimColor = ScrimColor,
    isDark = false
)

private val LightColorPalette = SimpleColors(
    pageBackground = PageBackground,
    almostBlack = AlmostBlack,
    white = White,
    green = Green,
    deepGray = DeepGrey,
    lightGray = LightGrey,
    divider = LightGrey.copy(0.3f),
    textColor = SearchText,
    captionColor = SearchCaption,
    scrimColor = ScrimColor,
    isDark = false
)

@Composable
fun SimpleTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = colors.pageBackground
        )
    }

    ProvideSimpleColors(colors) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object SimpleTheme {
    val colors: SimpleColors
        @Composable
        get() = LocalSimpleColors.current
}

@Stable
class SimpleColors(
    pageBackground: Color,
    almostBlack: Color,
    white: Color,
    green: Color,
    deepGray: Color,
    lightGray: Color,
    divider: Color,
    textColor: Color,
    captionColor: Color,
    scrimColor: Color,
    isDark: Boolean
) {
    var pageBackground by mutableStateOf(pageBackground)
        private set
    var almostBlack by mutableStateOf(almostBlack)
        private set
    var white by mutableStateOf(white)
        private set
    var green by mutableStateOf(green)
        private set
    var deepGray by mutableStateOf(deepGray)
        private set
    var lightGray by mutableStateOf(lightGray)
        private set
    var divider by mutableStateOf(divider)
        private set
    var textColor by mutableStateOf(textColor)
        private set
    var captionColor by mutableStateOf(captionColor)
        private set
    var scrimColor by mutableStateOf(scrimColor)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: SimpleColors) {
        pageBackground = other.pageBackground
        almostBlack = other.almostBlack
        white = other.white
        green = other.green
        deepGray = other.deepGray
        lightGray = other.lightGray
        divider = other.divider
        textColor = other.textColor
        textColor = other.captionColor
        scrimColor = other.scrimColor
        isDark = other.isDark
    }

    fun copy(): SimpleColors = SimpleColors(
        pageBackground = pageBackground,
        almostBlack = almostBlack,
        white = white,
        green = green,
        deepGray = deepGray,
        lightGray = lightGray,
        divider = divider,
        textColor = SearchText,
        captionColor = SearchCaption,
        scrimColor = ScrimColor,
        isDark = isDark
    )
}

@Composable
fun ProvideSimpleColors(
    colors: SimpleColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalSimpleColors provides colorPalette, content = content)
}

private val LocalSimpleColors = staticCompositionLocalOf<SimpleColors> {
    error("No SimpleColorPalette provided")
}

fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onBackground = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)
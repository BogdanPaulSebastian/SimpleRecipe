package net.paulbogdan.simplerecipe.ui

import androidx.compose.runtime.compositionLocalOf

data class GlobalBottomSheet(
    val show: (() -> Unit)? = null,
    val hide: (() -> Unit)? = null
)

var LocalShowFilterSheet = compositionLocalOf {
    GlobalBottomSheet()
}

var LocalSelectThemeSheet = compositionLocalOf {
    GlobalBottomSheet()
}
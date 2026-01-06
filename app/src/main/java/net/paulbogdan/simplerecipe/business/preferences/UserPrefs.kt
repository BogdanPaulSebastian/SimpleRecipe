package net.paulbogdan.simplerecipe.business.preferences

import net.paulbogdan.simplerecipe.model.Recipe
import net.paulbogdan.simplerecipe.ui.bottomsheets.SimpleThemeMode

interface UserPrefs {

    fun saveFavorites(favorites: List<Recipe>)
    fun getFavorites(): MutableList<Recipe>

    fun setShowConfirmationDialog(show: Boolean)
    fun getShowConfirmationDialog(): Boolean

    fun setSeenDisclaimerOnce(seen: Boolean)
    fun seenDisclaimerOnce(): Boolean

    fun setJavaScriptPreference(enabled: Boolean)
    fun getJavaScripPreference(): Boolean

    fun setSimpleThemeMode(mode: SimpleThemeMode)
    fun getSimpleThemeMode(): SimpleThemeMode

}
package net.paulbogdan.simplerecipe.business.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.model.Recipe
import net.paulbogdan.simplerecipe.ui.bottomsheets.SimpleThemeMode

class UserPrefsImpl(private val context: Context) : UserPrefs {

    private val defaults: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val keyFavorites = "simple.favorites"
        const val keyShowConfirmationDialog = "simple.confirmation.dialog"
        const val keySeenDisclaimerDialog = "simple.disclaimer.dialog"
        const val keyEnableJavaScript = "simple.javascript"
        const val keySimpleThemeMode = "simple.theme"
    }

    override fun saveFavorites(favorites: List<Recipe>) {
        val jsonData = Gson().toJson(favorites).toString()
        defaults.edit().putString(keyFavorites, jsonData).apply()
    }

    override fun getFavorites(): MutableList<Recipe> {
        val stringJson = defaults.getString(keyFavorites, null) ?: return mutableListOf()
        val type = object : TypeToken<List<Recipe>>() {}.type
        return Gson().fromJson(stringJson, type)
    }

    override fun setShowConfirmationDialog(show: Boolean) {
        defaults.edit().putBoolean(keyShowConfirmationDialog, show).apply()
    }

    override fun getShowConfirmationDialog(): Boolean {
        return  defaults.getBoolean(keyShowConfirmationDialog, true)
    }

    override fun setSeenDisclaimerOnce(seen: Boolean) {
        defaults.edit().putBoolean(keySeenDisclaimerDialog, seen).apply()
    }

    override fun seenDisclaimerOnce(): Boolean {
        return  defaults.getBoolean(keySeenDisclaimerDialog, false)
    }

    override fun setJavaScriptPreference(enabled: Boolean) {
        defaults.edit().putBoolean(keyEnableJavaScript, enabled).apply()
    }

    override fun getJavaScripPreference(): Boolean {
        return  defaults.getBoolean(keyEnableJavaScript, true)
    }

    override fun setSimpleThemeMode(mode: SimpleThemeMode) {
        defaults.edit().putInt(keySimpleThemeMode, mode.id).apply()
    }

    override fun getSimpleThemeMode(): SimpleThemeMode {
        val id = defaults.getInt(keySimpleThemeMode, 1)
        return SimpleThemeMode.getSimpleThemeMode(id).first {it.id == id}
    }

}
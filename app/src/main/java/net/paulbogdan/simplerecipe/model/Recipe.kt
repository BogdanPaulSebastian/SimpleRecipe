package net.paulbogdan.simplerecipe.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    @SerializedName("label")
    val label: String,
    val image: String,
    val uri: String,
    val url: String,
    val shareAs: String,
    val yield: Double?,
    val calories: Double?,
    val healthLabels: ArrayList<String>,
    val ingredients: ArrayList<Ingredient>
) : Parcelable {

    companion object{
        fun emptyRecipe() = Recipe(
            label = "",
            image = "",
            uri = "",
            url = "",
            shareAs = "",
            yield = 0.0,
            calories = 0.0,
            healthLabels = arrayListOf(),
            ingredients = arrayListOf()
        )
    }

}

@Parcelize
data class Ingredient(
    val text: String,
    val quantity: Double?,
    val measure: String,
    val food: String,
    val weight: Double?,
    val foodId: String,
) : Parcelable
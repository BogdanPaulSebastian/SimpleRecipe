package net.paulbogdan.simplerecipe.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.paulbogdan.simplerecipe.BuildConfig
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.model.ApiResponse
import net.paulbogdan.simplerecipe.model.Recipe
import net.paulbogdan.simplerecipe.network.ApiService
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val apiService: ApiService,
    val userPrefs: UserPrefs
) : BaseViewModel() {

    var recipeQuery by mutableStateOf("")
    var favoriteSearchQuery by mutableStateOf("")
    private val _apiResponse = MutableLiveData<ApiResponse>()
    val apiResponse: LiveData<ApiResponse> = _apiResponse
    var searchResult = mutableStateListOf<Recipe>()
    var filteredHealthLabelsList = mutableListOf<String>()
    var filteredCuisineList = mutableListOf<String>()
    var favoriteRecipeList by mutableStateOf(userPrefs.getFavorites())
        private set
    var activeRecipe by mutableStateOf(Recipe.emptyRecipe())
    var isBrowsing = mutableStateOf(false)
    var savedRoute = mutableStateOf("")

    fun getRecipe(query: String, healthFilters: Array<String>, cuisineType: Array<String>) {
        notifyIsLoading()
        disposeContainer.add(
            apiService.getRecipe(
                type = "public",
                q = query,
                app_id = BuildConfig.API_ID,
                app_key = BuildConfig.API_KEY,
                health = healthFilters,
                cuisineType = cuisineType,
                imageSize = arrayOf("LARGE"),
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {

                        _apiResponse.value = response.body()
                        if (!searchResult.isEmpty()) searchResult.clear()

                        response.body()?.hits?.forEach { hit ->
                            hit.recipe.let {
                                searchResult.add(
                                    hit.recipe
                                )
                            }
                        }
                        Log.d("SEARCH RES", searchResult.toString())

                    } else {
                        Log.d("SEARCH SUCCESS ERR", response.toString())
                        this.onError = response.message()
                        this.hasError = true
                    }
                    notifyFinishedLoading()
                },
                    { err ->
                        this.onError = err.toString()
                        this.hasError = true
                        notifyFinishedLoading()
                        Log.d("SEARCH ERROR", err.toString())
                    }
                )
        )
    }

    fun nextPage() {

        val startIndex = apiResponse.value!!._links.next.href.indexOf("_cont=") + 6
        val endIndex = apiResponse.value!!._links.next.href.indexOf("&imageSize=")

        notifyIsLoading()
        disposeContainer.add(
            apiService.getNextPage(
                q = recipeQuery,
                app_key = BuildConfig.API_KEY,
                cont = apiResponse.value!!._links.next.href.substring(startIndex, endIndex) ,
                imageSize = arrayOf("LARGE"),
                type = "public",
                app_id = BuildConfig.API_ID,
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {
                        _apiResponse.value = response.body()

                        response.body()?.hits?.forEach { hit ->
                            hit.recipe.let {
                                searchResult.add(
                                    hit.recipe
                                )
                            }
                        }
                        Log.d("NEXT PAGE RES", searchResult.toString())

                    } else {
                        Log.d("NEXT PAGE SUCCESS ERR", response.toString())
                        this.onError = response.message()
                        this.hasError = true
                    }
                    notifyFinishedLoading()
                },
                    { err ->
                        this.onError = err.toString()
                        this.hasError = true
                        notifyFinishedLoading()
                        Log.d("NEXT PAGE ERROR", err.toString())
                    }
                )
        )
    }

    fun createFavoriteList(recipe: Recipe){
        val mutableFavoritesList = favoriteRecipeList.toMutableList()

        if(mutableFavoritesList.contains(recipe)){
            mutableFavoritesList.remove(recipe)
        } else {
            mutableFavoritesList.add(recipe)
        }

        userPrefs.saveFavorites(mutableFavoritesList)
        favoriteRecipeList = mutableFavoritesList

    }

    fun createHealthFilterList(filter: String) {
        if (filteredHealthLabelsList.contains(filter)) {
            filteredHealthLabelsList.remove(filter)
        } else {
            filteredHealthLabelsList.add(filter)
        }
    }

    fun createCuisineFilterList(filter: String) {
        if (filteredCuisineList.contains(filter)) {
            filteredCuisineList.remove(filter)
        } else {
            filteredCuisineList.add(filter)
        }
    }
}
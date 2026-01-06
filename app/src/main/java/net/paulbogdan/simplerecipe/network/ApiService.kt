package net.paulbogdan.simplerecipe.network

import io.reactivex.Single
import net.paulbogdan.simplerecipe.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/recipes/v2")
    fun getRecipe(@Query("type") type: String, @Query("q") q: String, @Query("app_id") app_id: String, @Query("app_key") app_key: String, @Query("health") health: Array<String>, @Query("cuisineType") cuisineType: Array<String>, @Query("imageSize") imageSize: Array<String> ): Single<Response<ApiResponse>>

    @GET("/api/recipes/v2")
    fun getNextPage(@Query("q") q: String, @Query("app_key") app_key: String, @Query("_cont", encoded = true) cont: String, @Query("imageSize") imageSize: Array<String>, @Query("type") type: String, @Query("app_id") app_id: String ): Single<Response<ApiResponse>>
}
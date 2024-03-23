package com.example.recipefeed.data.recipe.api

import com.example.recipefeed.data.recipe.model.recipe.Recipe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipesService {
    @GET("getAll")
    suspend fun getAll(): List<Recipe>

    @GET("getById/{id}")
    suspend fun getById(@Path("id") id: Int): Recipe

    @POST("addRecipe")
    suspend fun addRecipe(@Body recipe: Recipe)

}
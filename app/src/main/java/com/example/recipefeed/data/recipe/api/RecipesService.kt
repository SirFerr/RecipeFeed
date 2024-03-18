package com.example.recipefeed.data.recipe.api

import com.example.recipefeed.data.recipe.model.Recipes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecipesService{
    @GET("/getAll")
    suspend fun getAll(): Recipes

    @POST("/addRecipe")
    suspend fun addRecipe(@Body recipes: Recipes)
}
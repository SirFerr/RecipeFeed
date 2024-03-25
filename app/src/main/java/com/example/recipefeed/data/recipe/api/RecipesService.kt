package com.example.recipefeed.data.recipe.api

import com.example.recipefeed.data.recipe.model.recipe.Recipe
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RecipesService {
    @GET("recipe/getAll")
    suspend fun getAll(): List<Recipe>

    @GET("recipe/getById/{id}")
    suspend fun getById(@Path("id") id: Int): Recipe

    @Multipart
    @POST("recipe/addRecipe")
    suspend fun addRecipe(
        @Part("data") recipe: Recipe, @Part imagePart: MultipartBody.Part
    )

}
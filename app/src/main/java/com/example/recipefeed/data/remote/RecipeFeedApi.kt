package com.example.recipefeed.data.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RecipeFeedApi {

    @GET("api/recipe/getAll")
    suspend fun getAll(
    ): Response<List<Recipe>>

    @GET("api/recipe/getById/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Response<Recipe>

    @Multipart
    @POST("api/recipe/addRecipe")
    suspend fun addRecipe(
        @Part("data") recipe: Recipe,
        @Part imagePart: MultipartBody.Part
    ): Response<Recipe>

    @GET("api/recipe/getByNameLike/{name}")
    suspend fun getByName(
        @Path("name") name: String,
    ): Response<List<Recipe>>

    @GET("api/recipe/getRandom")
    suspend fun getRandom(): Response<Recipe>

    @DELETE("api/recipe/deleteRecipe/{id}")
    suspend fun deleteById(
        @Path("id") id: Int,
    ): Response<Recipe>

    @Multipart
    @POST("api/recipe/updateRecipe/{id}")
    suspend fun updateRecipe(
        @Path("id") id: Int,
        @Part("data") recipe: Recipe,
        @Part imagePart: MultipartBody.Part,
    ): Response<Recipe>

    @POST("auth/signup")
    suspend fun signUp(
        @Body auth: Auth
    ): Response<Auth>

    @POST("auth/signin")
    suspend fun signIn(
        @Body auth: Auth
    ): Response<Auth>

    @GET("/api/recipe/getUsersRecipes")
    suspend fun getUsersRecipes(
    ): Response<List<Recipe>>

    @POST("/api/recipe/addToFavourites")
    suspend fun addToFavourites(
        @Body recipe: Recipe
    ): Response<FavouriteRecipe>

    @GET("/api/recipe/getFavouritesRecipes")
    suspend fun getFavouritesRecipes(
    ): Response<List<FavouriteRecipe>>
}
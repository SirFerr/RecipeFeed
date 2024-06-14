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

    companion object {
        private const val API_RECIPE_START = "api/recipe/"
        private const val API_AUTH_START = "auth/"
    }

    //Recipe
    @GET(API_RECIPE_START + "getAll")
    suspend fun getAllRecipes(
    ): Response<List<Recipe>>

    @GET(API_RECIPE_START + "getById/{id}")
    suspend fun getRecipeById(
        @Path("id") id: Int
    ): Response<Recipe>

    @Multipart
    @POST(API_RECIPE_START + "addRecipe")
    suspend fun addRecipe(
        @Part("data") recipe: Recipe,
        @Part imagePart: MultipartBody.Part
    ): Response<Recipe>

    @GET(API_RECIPE_START + "getByNameLike/{name}")
    suspend fun getRecipeByName(
        @Path("name") name: String,
    ): Response<List<Recipe>>

    @GET(API_RECIPE_START + "getRandom")
    suspend fun getRandomRecipe(): Response<Recipe>

    @DELETE(API_RECIPE_START + "deleteRecipe/{id}")
    suspend fun deleteRecipeById(
        @Path("id") id: Int,
    ): Response<Recipe>

    @Multipart
    @POST(API_RECIPE_START + "updateRecipe/{id}")
    suspend fun updateRecipe(
        @Path("id") id: Int,
        @Part("data") recipe: Recipe,
        @Part imagePart: MultipartBody.Part,
    ): Response<Recipe>

    @GET(API_RECIPE_START + "getUsersRecipes")
    suspend fun getUsersRecipes(
    ): Response<List<Recipe>>

    @POST(API_RECIPE_START + "addToFavourites")
    suspend fun addRecipeToFavourites(
        @Body recipe: Recipe
    ): Response<FavouriteRecipe>

    @GET(API_RECIPE_START + "getFavouritesRecipes")
    suspend fun getFavouritesRecipes(
    ): Response<List<FavouriteRecipe>>


    //Auth
    @POST(API_AUTH_START + "signup")
    suspend fun signUp(
        @Body auth: Auth
    ): Response<Auth>

    @POST(API_AUTH_START + "signin")
    suspend fun signIn(
        @Body auth: Auth
    ): Response<Auth>
}
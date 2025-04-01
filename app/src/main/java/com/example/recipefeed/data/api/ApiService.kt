package com.example.recipefeed.data.api

import com.example.recipefeed.data.models.*
import com.example.recipefeed.data.models.Tag
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Users
    @GET("users/{user_id}")
    suspend fun getUserById(@Path("user_id") userId: Int): Response<User>

    @POST("users/register")
    suspend fun registerUser(@Body user: UserCreate): Response<User>

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<Token>

    @GET("users/me/token")
    suspend fun getCurrentUser(): Response<User>

    // Recipes
    @GET("recipes/{recipe_id}")
    suspend fun getRecipeById(@Path("recipe_id") recipeId: Int): Response<Recipe>

    @GET("recipes/random")
    suspend fun getRandomRecipe(): Response<Recipe>

    @Multipart
    @POST("recipes/create")
    suspend fun createRecipe(
        @Query("name") name: String,
        @Query("description") description: String?,
        @Query("steps") steps: String?,
        @Part image: MultipartBody.Part?
    ): Response<Recipe>

    @Multipart
    @PUT("recipes/{recipe_id}/edit")
    suspend fun editRecipe(
        @Path("recipe_id") recipeId: Int,
        @Query("name") name: String?,
        @Query("description") description: String?,
        @Query("steps") steps: String?,
        @Part image: MultipartBody.Part?
    ): Response<Recipe>

    @DELETE("recipes/{recipe_id}/delete")
    suspend fun deleteRecipe(@Path("recipe_id") recipeId: Int): Response<Map<String, String>>

    @PUT("recipes/{recipe_id}/approve")
    suspend fun approveRecipe(@Path("recipe_id") recipeId: Int): Response<Recipe>

    @PUT("recipes/{recipe_id}/reject")
    suspend fun rejectRecipe(
        @Path("recipe_id") recipeId: Int,
        @Query("reason") reason: String
    ): Response<Recipe>

    @GET("recipes/search/name")
    suspend fun searchRecipesByName(
        @Query("query") query: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<List<Recipe>>

    @GET("recipes/search/tags")
    suspend fun searchRecipesByTags(
        @Query("tag_ids") tagIds: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<List<Recipe>>

    @GET("recipes/user/created")
    suspend fun getUserRecipes(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<List<Recipe>>

    @GET("recipes/{recipe_id}/nutrition")
    suspend fun calculateNutrition(@Path("recipe_id") recipeId: Int): Response<Nutrition>

    @GET("recipes/on-approve")
    suspend fun getRecipesOnApprove(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<List<Recipe>>

    // Tags
    @GET("tags/{tag_id}")
    suspend fun getTagById(@Path("tag_id") tagId: Int): Response<Tag>

    @POST("tags/create")
    suspend fun createTag(@Body tag: TagCreate): Response<Tag>

    @GET("tags/list")
    suspend fun getTagsList(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<List<Tag>>

    @DELETE("tags/{tag_id}/delete")
    suspend fun deleteTag(@Path("tag_id") tagId: Int): Response<Map<String, String>>

    // Recipe Tags
    @GET("recipe-tags/{recipe_id}/list")
    suspend fun getRecipeTags(@Path("recipe_id") recipeId: Int): Response<List<RecipeTag>>

    @PUT("recipe-tags/{recipe_id}/update")
    suspend fun updateRecipeTags(
        @Path("recipe_id") recipeId: Int,
        @Body tagIds: List<Int>
    ): Response<List<RecipeTag>>

    // Ingredients (удалены методы, связанные с управлением ингредиентами)
    @GET("ingredients/external/search")
    suspend fun getExternalIngredients(@Query("query") query: String): Response<List<Map<String, Any>>>

    // Recipe Ingredients
    @GET("recipe-ingredients/{recipe_id}/list")
    suspend fun getRecipeIngredients(@Path("recipe_id") recipeId: Int): Response<List<RecipeIngredient>>

    @PUT("recipe-ingredients/{recipe_id}/update")
    suspend fun updateRecipeIngredients(
        @Path("recipe_id") recipeId: Int,
        @Body ingredients: List<RecipeIngredientCreate>
    ): Response<List<RecipeIngredient>>

    // Favorites
    @GET("favorites/list")
    suspend fun getFavorites(): Response<List<Favorite>>

    @POST("favorites/add")
    suspend fun addFavorite(@Body favorite: FavoriteCreate): Response<Favorite>

    @DELETE("favorites/{recipe_id}/delete")
    suspend fun deleteFavorite(@Path("recipe_id") recipeId: Int): Response<Map<String, String>>

    @GET("favorites/{recipe_id}/is-favorite")
    suspend fun isRecipeFavorite(@Path("recipe_id") recipeId: Int): Response<Boolean>

    // Comments
    @GET("comments/{recipe_id}/list")
    suspend fun getRecipeComments(
        @Path("recipe_id") recipeId: Int,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<List<Comment>>

    @POST("comments/create")
    suspend fun createComment(
        @Query("recipe_id") recipeId: Int,
        @Body comment: CommentCreate
    ): Response<Comment>

    // Moderators
    @POST("moderators/{user_id}")
    suspend fun addModerator(@Path("user_id") userId: Int): Response<Map<String, String>>

    @GET("moderators/")
    suspend fun getModerators(): Response<List<Int>>

    @DELETE("moderators/{user_id}")
    suspend fun removeModerator(@Path("user_id") userId: Int): Response<Map<String, String>>

    @DELETE("comments/{comment_id}/delete")
    suspend fun deleteComment(@Path("comment_id") commentId: Int): Response<Map<String, String>>

    @PUT("comments/{comment_id}/reject")
    suspend fun rejectComment(
        @Path("comment_id") commentId: Int,
        @Query("reason") reason: String
    ): Response<Comment>
}
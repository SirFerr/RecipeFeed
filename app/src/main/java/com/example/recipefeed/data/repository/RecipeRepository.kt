package com.example.recipefeed.data.repository

import com.example.recipefeed.data.api.ApiService
import com.example.recipefeed.data.local.SearchHistorySharedPreferencesManager
import com.example.recipefeed.data.models.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val apiService: ApiService,
    private val searchHistoryManager: SearchHistorySharedPreferencesManager
) {

    // Users
    suspend fun getUserById(userId: Int): Result<User> = handleResponse { apiService.getUserById(userId) }

    suspend fun registerUser(user: UserCreate): Result<User> = handleResponse { apiService.registerUser(user) }

    suspend fun login(username: String, password: String): Result<Token> = handleResponse { apiService.login(username, password) }

    suspend fun getCurrentUser(): Result<User> = handleResponse { apiService.getCurrentUser() }

    // Recipes
    suspend fun getRecipeById(recipeId: Int): Result<Recipe> = handleResponse { apiService.getRecipeById(recipeId) }

    suspend fun getRandomRecipe(): Result<Recipe> = handleResponse { apiService.getRandomRecipe() }

    suspend fun createRecipe(name: String, description: String?, steps: String?, imageFile: File?): Result<Recipe> {
        val imagePart = imageFile?.let {
            val requestFile = it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", it.name, requestFile)
        }
        return handleResponse { apiService.createRecipe(name, description, steps, imagePart) }
    }

    suspend fun editRecipe(recipeId: Int, name: String?, description: String?, steps: String?, imageFile: File?): Result<Recipe> {
        val imagePart = imageFile?.let {
            val requestFile = it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", it.name, requestFile)
        }
        return handleResponse { apiService.editRecipe(recipeId, name, description, steps, imagePart) }
    }

    suspend fun deleteRecipe(recipeId: Int): Result<Map<String, String>> = handleResponse { apiService.deleteRecipe(recipeId) }

    suspend fun approveRecipe(recipeId: Int): Result<Recipe> = handleResponse { apiService.approveRecipe(recipeId) }

    suspend fun rejectRecipe(recipeId: Int, reason: String): Result<Recipe> = handleResponse { apiService.rejectRecipe(recipeId, reason) }

    suspend fun searchRecipesByName(query: String, skip: Int, limit: Int): Result<List<Recipe>> = handleResponse { apiService.searchRecipesByName(query, skip, limit) }

    suspend fun searchRecipesByTags(tagIds: List<Int>, skip: Int, limit: Int): Result<List<Recipe>> {
        val tagIdsString = tagIds.joinToString(",")
        return handleResponse { apiService.searchRecipesByTags(tagIdsString, skip, limit) }
    }

    suspend fun getUserRecipes(skip: Int, limit: Int): Result<List<Recipe>> = handleResponse { apiService.getUserRecipes(skip, limit) }

    suspend fun calculateNutrition(recipeId: Int): Result<Nutrition> = handleResponse { apiService.calculateNutrition(recipeId) }

    suspend fun getRecipesOnApprove(skip: Int, limit: Int): Result<List<Recipe>> =
        handleResponse { apiService.getRecipesOnApprove(skip, limit) }

    // Tags
    suspend fun getTagById(tagId: Int): Result<Tag> = handleResponse { apiService.getTagById(tagId) }

    suspend fun createTag(tag: TagCreate): Result<Tag> = handleResponse { apiService.createTag(tag) }

    suspend fun getTagsList(skip: Int, limit: Int): Result<List<Tag>> = handleResponse { apiService.getTagsList(skip, limit) }

    suspend fun deleteTag(tagId: Int): Result<Map<String, String>> = handleResponse { apiService.deleteTag(tagId) }

    // Recipe Tags
    suspend fun getRecipeTags(recipeId: Int): Result<List<RecipeTag>> = handleResponse { apiService.getRecipeTags(recipeId) }

    suspend fun updateRecipeTags(recipeId: Int, tagIds: List<Int>): Result<List<RecipeTag>> = handleResponse { apiService.updateRecipeTags(recipeId, tagIds) }

    // Ingredients
    suspend fun getIngredientById(ingredientId: Int): Result<Ingredient> = handleResponse { apiService.getIngredientById(ingredientId) }

    suspend fun createIngredient(ingredient: IngredientCreate): Result<Ingredient> = handleResponse { apiService.createIngredient(ingredient) }

    suspend fun getIngredientsList(skip: Int, limit: Int): Result<List<Ingredient>> = handleResponse { apiService.getIngredientsList(skip, limit) }

    suspend fun deleteIngredient(ingredientId: Int): Result<Map<String, String>> = handleResponse { apiService.deleteIngredient(ingredientId) }

    suspend fun getExternalIngredients(query: String): Result<List<Map<String, Any>>> = handleResponse { apiService.getExternalIngredients(query) }

    // Recipe Ingredients
    suspend fun getRecipeIngredients(recipeId: Int): Result<List<RecipeIngredient>> = handleResponse { apiService.getRecipeIngredients(recipeId) }

    suspend fun updateRecipeIngredients(recipeId: Int, ingredients: List<RecipeIngredientCreate>): Result<List<RecipeIngredient>> = handleResponse { apiService.updateRecipeIngredients(recipeId, ingredients) }

    // Favorites
    suspend fun getFavorites(): Result<List<Favorite>> = handleResponse { apiService.getFavorites() }

    suspend fun addFavorite(favorite: FavoriteCreate): Result<Favorite> = handleResponse { apiService.addFavorite(favorite) }

    suspend fun deleteFavorite(recipeId: Int): Result<Map<String, String>> = handleResponse { apiService.deleteFavorite(recipeId) }

    // Comments
    suspend fun getRecipeComments(recipeId: Int, skip: Int, limit: Int): Result<List<Comment>> = handleResponse { apiService.getRecipeComments(recipeId, skip, limit) }

    suspend fun createComment(recipeId: Int, comment: CommentCreate): Result<Comment> = handleResponse { apiService.createComment(recipeId, comment) }

    suspend fun deleteComment(commentId: Int): Result<Map<String, String>> = handleResponse { apiService.deleteComment(commentId) }

    suspend fun rejectComment(commentId: Int, reason: String): Result<Comment> = handleResponse { apiService.rejectComment(commentId, reason) }

    // Методы для работы с историей поиска
    suspend fun getSearchHistory(): List<String> {
        return searchHistoryManager.getSearchHistory()
    }

    suspend fun saveSearchRequest(value: String) {
        searchHistoryManager.saveRequest(value)
    }

    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
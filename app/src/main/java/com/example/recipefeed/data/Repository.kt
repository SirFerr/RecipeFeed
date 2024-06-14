package com.example.recipefeed.data

import com.example.recipefeed.data.local.SearchHistorySharedPreferencesManager
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.Auth
import com.example.recipefeed.data.remote.FavouriteRecipe
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val recipeFeedApi: RecipeFeedApi,
    private val searchHistorySharedPreferencesManager: SearchHistorySharedPreferencesManager,
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager
) {

    //RecipeFeedApi
    suspend fun getAllRecipes(): Response<List<Recipe>> {
        return recipeFeedApi.getAllRecipes()
    }

    suspend fun getRecipeById(id: Int): Response<Recipe> {
        return recipeFeedApi.getRecipeById(id)
    }

    suspend fun addRecipe(recipe: Recipe, imagePart: MultipartBody.Part): Response<Recipe> {
        return recipeFeedApi.addRecipe(recipe, imagePart)
    }

    suspend fun getRecipesByName(name: String): Response<List<Recipe>> {
        return recipeFeedApi.getRecipeByName(name)
    }

    suspend fun getRandomRecipe(): Response<Recipe> {
        return recipeFeedApi.getRandomRecipe()
    }

    suspend fun deleteRecipeById(id: Int): Response<Recipe> {
        return recipeFeedApi.deleteRecipeById(id)
    }

    suspend fun updateRecipe(id: Int, recipe: Recipe, imagePart: MultipartBody.Part): Response<Recipe> {
        return recipeFeedApi.updateRecipe(id, recipe, imagePart)
    }

    suspend fun signUp(auth: Auth): Response<Auth> {
        return recipeFeedApi.signUp(auth)
    }

    suspend fun signIn(auth: Auth): Response<Auth> {
        return recipeFeedApi.signIn(auth)
    }

    suspend fun getUsersRecipes(): Response<List<Recipe>> {
        return recipeFeedApi.getUsersRecipes()
    }

    suspend fun addToFavourites(recipe: Recipe): Response<FavouriteRecipe> {
        return recipeFeedApi.addRecipeToFavourites(recipe)
    }

    suspend fun getFavouritesRecipes(): Response<List<FavouriteRecipe>> {
        return recipeFeedApi.getFavouritesRecipes()
    }


    //TokenSharedPreferencesManager
    fun saveToken(token: String) {
       return tokenSharedPreferencesManager.saveToken(token)
    }

    fun getToken(): String {
       return tokenSharedPreferencesManager.getToken()
    }

    fun deleteToken() {
       return tokenSharedPreferencesManager.deleteToken()
    }


    //SearchHistorySharedPreferencesManager
    fun saveRequest(value: String) {
        return searchHistorySharedPreferencesManager.saveRequest(value)
    }


    fun getSearchHistory(): List<String> {
        return searchHistorySharedPreferencesManager.getSearchHistory()
    }

}
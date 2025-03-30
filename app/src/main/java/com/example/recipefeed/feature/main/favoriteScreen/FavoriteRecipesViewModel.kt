package com.example.recipefeed.view.mainMenu.favoriteScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.Recipe
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    private var _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    init {
        getFavouritesRecipes()
    }

    fun getFavouritesRecipes() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val favoritesResult = repository.getFavorites()
                if (favoritesResult.isSuccess) {
                    val favoriteRecipes = favoritesResult.getOrNull() ?: emptyList()
                    // Получаем рецепты по их ID из избранного
                    val recipesList = mutableListOf<Recipe>()
                    for (favorite in favoriteRecipes) {
                        val recipeResult = repository.getRecipeById(favorite.recipeId)
                        if (recipeResult.isSuccess) {
                            recipeResult.getOrNull()?.let { recipesList.add(it) }
                        }
                    }
                    _recipes.value = recipesList
                    _isSuccessful.value = true
                } else {
                    _isSuccessful.value = false
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
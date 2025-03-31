package com.example.recipefeed.view.mainMenu.favoriteScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.FavoriteCreate
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

    private var _favoriteStatus = mutableStateOf<Map<Int, Boolean>>(emptyMap())
    val favoriteStatus: State<Map<Int, Boolean>> = _favoriteStatus

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
                    val recipesList = mutableListOf<Recipe>()
                    val statusMap = mutableMapOf<Int, Boolean>()
                    for (favorite in favoriteRecipes) {
                        val recipeResult = repository.getRecipeById(favorite.recipeId)
                        if (recipeResult.isSuccess) {
                            recipeResult.getOrNull()?.let {
                                recipesList.add(it)
                                statusMap[it.id] = true
                            }
                        }
                    }
                    _recipes.value = recipesList
                    _favoriteStatus.value = statusMap
                    _isSuccessful.value = true
                } else {
                    _isSuccessful.value = false
                    _favoriteStatus.value = emptyMap()
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
                _favoriteStatus.value = emptyMap()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            try {
                val currentUserResult = repository.getCurrentUser()
                if (currentUserResult.isSuccess) {
                    val user = currentUserResult.getOrNull() ?: run {
                        println("User is null despite success")
                        return@launch
                    }
                    val userId = user.id
                    val isFavorite = _favoriteStatus.value[recipeId] ?: false
                    val result = if (isFavorite) {
                        repository.deleteFavorite(recipeId)
                    } else {
                        repository.addFavorite(FavoriteCreate(userId = userId, recipeId = recipeId))
                    }
                    if (result.isSuccess) {
                        _favoriteStatus.value = _favoriteStatus.value.toMutableMap().apply {
                            this[recipeId] = !isFavorite
                        }
                        if (isFavorite) {
                            _recipes.value = _recipes.value.filter { it.id != recipeId }
                        }
                    } else {
                        println("Toggle failed: ${result.exceptionOrNull()?.message}")
                    }
                } else {
                    println("getCurrentUser failed: ${currentUserResult.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                println("Exception in toggleFavorite: ${e.message}")
            }
        }
    }
}
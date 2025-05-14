package com.example.recipefeed.feature.main.recipesOnApprove

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
class RecipesOnApproveViewModel @Inject constructor(
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
        getRecipesOnApprove()
    }

    fun getRecipesOnApprove() {
        viewModelScope.launch {
            try {
                val currentUserResult = repository.getCurrentUser()
                _isLoading.value = true
                val result = repository.getRecipesOnApprove(skip = 0, limit = 20)
                _isSuccessful.value = result.isSuccess
                if (result.isSuccess) {
                    val recipesList = result.getOrNull()?.filter { it.isOnApprove } ?: emptyList()
                    val translatedRecipes = recipesList.map { it.translateToRussian() } // ⬅️ Перевод
                    _recipes.value = translatedRecipes
                    checkFavoriteStatus(translatedRecipes)
                } else {
                    _recipes.value = emptyList()
                    _favoriteStatus.value = emptyMap()
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
                _recipes.value = emptyList()
                _favoriteStatus.value = emptyMap()
            } finally {
                _isLoading.value = false
            }
        }
    }


    private suspend fun checkFavoriteStatus(recipes: List<Recipe>) {
        val statusMap = mutableMapOf<Int, Boolean>()
        recipes.forEach { recipe ->
            val isFavoriteResult = repository.isRecipeFavorite(recipe.id)
            statusMap[recipe.id] = isFavoriteResult.getOrNull() ?: false
        }
        _favoriteStatus.value = statusMap
    }

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            try {
                val currentUserResult = repository.getCurrentUser()
                if (currentUserResult.isSuccess) {
                    val userId = currentUserResult.getOrNull()?.id ?: return@launch
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
                    }
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }
}
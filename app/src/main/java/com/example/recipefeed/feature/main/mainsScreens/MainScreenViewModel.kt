package com.example.recipefeed.view.mainMenu.mainsScreens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.FavoriteCreate
import com.example.recipefeed.data.models.Recipe
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _mainState = mutableStateOf<MainState>(MainState.Loading)
    val mainState: State<MainState> = _mainState

    private val recipeQueue = mutableListOf<Recipe>()

    init {
        preloadRecipes()
    }

    fun onSwipeLeft() {
        viewModelScope.launch {
            if (recipeQueue.isNotEmpty()) {
                recipeQueue.removeAt(0)
                updateState()
                preloadRecipes()
            }
        }
    }

    fun onSwipeRight() {
        viewModelScope.launch {
            if (recipeQueue.isNotEmpty()) {
                val currentRecipe = recipeQueue.removeAt(0)
                addToFavourites(currentRecipe)
                updateState()
                preloadRecipes()
            }
        }
    }

    fun preloadRecipes() {
        viewModelScope.launch {
            if (recipeQueue.size <= 2) {
                try {
                    repeat(3 - recipeQueue.size) {
                        val result = repository.getRandomRecipe()
                        result.onSuccess { recipe ->
                            val translatedRecipe = recipe.translateToRussian() // 🔁 переводим
                            recipeQueue.add(translatedRecipe)
                            updateState()
                        }.onFailure { exception ->
                            Log.e("MainViewModel", "Fetch failed: ${exception.message}", exception)
                            if (exception.message?.contains("404") == true) {
                                _mainState.value = MainState.AllFavourited
                                Log.d("MainViewModel", "All recipes favorited (404)")
                            } else if (recipeQueue.isEmpty()) {
                                _mainState.value = MainState.Error(exception.message ?: "Неизвестная ошибка")
                            }
                            return@launch
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Exception: ${e.message}", e)
                    if (e.message?.contains("404") == true) {
                        _mainState.value = MainState.AllFavourited
                        Log.d("MainViewModel", "All recipes favorited (404)")
                    } else if (recipeQueue.isEmpty()) {
                        _mainState.value = MainState.Error(e.message ?: "Неизвестная ошибка")
                    }
                }
            }
        }
    }



    private fun updateState() {
        _mainState.value = if (recipeQueue.isNotEmpty()) {
            MainState.Success(recipeQueue.first())
        } else {
            MainState.Loading
        }
    }

    private fun addToFavourites(recipe: Recipe) {
        viewModelScope.launch {
            try {
                val favorite = FavoriteCreate(userId = recipe.userId, recipeId = recipe.id)
                repository.addFavorite(favorite)
                Log.d("MainViewModel", "Added to favorites: $recipe")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failed to add to favorites: ${e.message}", e)
            }
        }
    }
}

sealed class MainState {
    object Loading : MainState()
    data class Success(val currentRecipe: Recipe) : MainState()
    data class Error(val message: String) : MainState()
    object AllFavourited : MainState() // Добавляем новое состояние
}
package com.example.recipefeed.view.mainMenu.mainsScreens

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
class MainScreenViewModel @Inject constructor(
    private val repository: RecipeRepository // Явно указываем RecipeRepository
) : ViewModel() {

    private val _mainState = mutableStateOf<MainState>(MainState.Loading)
    val mainState: State<MainState> = _mainState

    private val _nextRecipe = mutableStateOf<Recipe>(Recipe(0, 0, "", null, java.util.Date(), null, false, null, null, 0)) // Начальное значение с учетом модели Recipe
    val nextRecipe: State<Recipe> = _nextRecipe

    init {
        fetchRandomRecipe(true)  // Загружаем первый рецепт для основного состояния
        fetchRandomRecipe(false) // Загружаем следующий рецепт для предпросмотра
    }

    fun onSwipeLeft() {
        _mainState.value = MainState.Success(nextRecipe.value) // Переключаем текущий рецепт на следующий
        fetchRandomRecipe(false) // Загружаем новый следующий рецепт
    }

    fun onSwipeRight() {
        _mainState.value = MainState.Success(nextRecipe.value) // Аналогично для правого свайпа
        fetchRandomRecipe(false)
    }

    private fun fetchRandomRecipe(isInitial: Boolean) {
        viewModelScope.launch {
            val result = repository.getRandomRecipe() // Используем метод из RecipeRepository
            result.onSuccess { recipe ->
                if (isInitial) {
                    _mainState.value = MainState.Success(recipe)
                } else {
                    _nextRecipe.value = recipe
                }
            }.onFailure { exception ->
                _mainState.value = MainState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun addToFavourites() {
        viewModelScope.launch {
            try {
                val currentState = mainState.value
                if (currentState is MainState.Success) {
                    val recipe = currentState.recipe
                    val favorite = FavoriteCreate(userId = recipe.userId, recipeId = recipe.id) // Создаем объект для добавления в избранное
                    repository.addFavorite(favorite) // Используем метод репозитория
                } else {
                    fetchRandomRecipe(true) // Если состояния нет, загружаем новый рецепт
                }
            } catch (e: Exception) {
                _mainState.value = MainState.Error(e.message ?: "Failed to add to favorites")
            }
        }
    }
}

sealed class MainState {
    object Loading : MainState()
    data class Success(val recipe: Recipe) : MainState()
    data class Error(val message: String) : MainState()
}
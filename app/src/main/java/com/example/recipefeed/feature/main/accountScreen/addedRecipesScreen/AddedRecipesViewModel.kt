package com.example.recipefeed.view.mainMenu.addedRecipesScreen

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
class AddedRecipesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccessful = mutableStateOf(true)
    val isSuccessful: State<Boolean> = _isSuccessful

    private var _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    init {
        getAddedRecipes()
    }

    fun getAddedRecipes() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getUserRecipes(skip = 0, limit = 100) // Можно настроить skip и limit
                _isSuccessful.value = result.isSuccess
                if (result.isSuccess) {
                    _recipes.value = result.getOrNull() ?: emptyList()
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
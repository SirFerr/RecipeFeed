package com.example.recipefeed.feature.main.recipesOnApprove

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
class RecipesOnApproveViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    private var _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    init {
        getRecipesOnApprove()
    }


    fun getRecipesOnApprove() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.getRecipesOnApprove(
                    skip = 0,  // Начальная позиция
                    limit = 20 // Ограничение на количество
                )
                _isSuccessful.value = result.isSuccess
                if (result.isSuccess) {
                    _recipes.value = result.getOrNull()?.filter { it.isOnApprove } ?: emptyList()
                } else {
                    _recipes.value = emptyList()
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
                _recipes.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
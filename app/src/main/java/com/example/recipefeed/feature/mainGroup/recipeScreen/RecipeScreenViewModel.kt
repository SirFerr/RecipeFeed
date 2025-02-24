package com.example.recipefeed.view.mainMenu.recipeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {


    private var _recipe = mutableStateOf(Recipe())
    val recipe: State<Recipe> = _recipe
    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private var _isLiked = mutableStateOf(false)
    val isLiked: State<Boolean> = _isLiked
    private var _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    fun changeLike() {
        _isLiked.value = !_isLiked.value
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response =
                    repository.getRecipeById(id)
                if (response.isSuccessful)
                    _recipe.value = response.body()!!
            } catch (e: Exception) {
            }
            _isLoading.value = false
        }
    }

    fun addToFavourites() {
        viewModelScope.launch {
            try {
                val response = repository.addToFavourites(recipe.value)
                _isSuccessful.value = response.isSuccessful
            } catch (e: Exception) {
                _isSuccessful.value = false
            }
        }
    }
}
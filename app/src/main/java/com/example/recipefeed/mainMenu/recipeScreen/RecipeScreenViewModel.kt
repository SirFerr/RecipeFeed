package com.example.recipefeed.view.mainMenu.recipeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.utils.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {

    val recipe = MutableStateFlow(Recipe())
    val isLoading = MutableStateFlow(false)
    val isLiked = MutableStateFlow(false)
    val isSuccessful = MutableStateFlow(true)



    fun changeLike() {
        isLiked.value = !isLiked.value
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response =
                    repository.getRecipeById(id,)
                if (response.isSuccessful)
                    recipe.value = response.body()!!
            } catch (e: Exception) {
            }
            isLoading.value = false
        }
    }

    fun addToFavourites() {
        viewModelScope.launch {
            try {
                val response = repository.addToFavourites(recipe.value)
                isSuccessful.value = response.isSuccessful
            } catch (e: Exception) {
                isSuccessful.value = false
            }
        }
    }
}
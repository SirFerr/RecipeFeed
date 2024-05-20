package com.example.recipefeed.view.mainMenu.mainsScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel

class MainScreenViewModel @Inject constructor(
    private val recipeFeedApi: RecipeFeedApi,
) : ViewModel() {

    val recipe = MutableStateFlow(Recipe())
    val isSuccessful = MutableStateFlow(true)
    var response = MutableStateFlow(Response.success(emptyList<Recipe>()))
    var isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    init {
        getResponse()
    }

    fun getResponse() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                response.value = recipeFeedApi.getAll()
                isSuccessful.value = response.value.isSuccessful
                if (response.value.isSuccessful) {
                    getRandomRecipe()
                } else {
                    errorMessage.value = "Failed to fetch recipes: ${response.value.message()}"
                }
            } catch (e: Exception) {
                isSuccessful.value = false
                errorMessage.value = "Error: ${e.message}"
            }
            isLoading.value = false
        }
    }

    fun getRandomRecipe() {
        viewModelScope.launch {
            if (response.value.isSuccessful) {
                val recipes = response.value.body().orEmpty()
                if (recipes.isNotEmpty()) {
                    recipe.value = recipes.random()
                } else {
                    errorMessage.value = "No recipes available."
                }
            }
        }
    }
}
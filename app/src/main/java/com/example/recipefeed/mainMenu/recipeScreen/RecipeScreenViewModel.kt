package com.example.recipefeed.view.mainMenu.recipeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val recipeFeedApi: RecipeFeedApi,
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager
) :
    ViewModel() {

    val idRecipe = MutableStateFlow(Recipe())
    val isLoading = MutableStateFlow(false)
    val isLiked = MutableStateFlow(false)


    fun changeLike() {
        isLiked.value = !isLiked.value
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response =
                    recipeFeedApi.getById(id, token = tokenSharedPreferencesManager.getToken())
                if (response.isSuccessful)
                    idRecipe.value = response.body()!!
            } catch (e: Exception) {
            }
            isLoading.value = false
        }
    }
}
package com.example.recipefeed.screens.mainMenu.recipeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.recipe.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) :
    ViewModel() {

    val idRecipe = MutableStateFlow(Recipe())
    val isLoading = MutableStateFlow(false)

    fun getById(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = recipeFeedApi.getById(id)
                if (response.isSuccessful)
                    idRecipe.value = response.body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
            isLoading.value = false
        }
    }
}
package com.example.recipefeed.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.RetrofitObject
import com.example.recipefeed.data.recipe.model.Recipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor() : ViewModel() {
    var recipes = MutableStateFlow(Recipes())

    init {
        getAllRecipes()
    }

    private fun getAllRecipes() {
        viewModelScope.launch {

            try {
                recipes.value = RetrofitObject.api.getAll()
            } catch (e: Exception) {

            }
        }
    }
}
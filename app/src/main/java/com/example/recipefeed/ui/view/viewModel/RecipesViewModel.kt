package com.example.recipefeed.ui.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.RetrofitObject
import com.example.recipefeed.data.model.Recipes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RecipesViewModel : ViewModel() {
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
package com.example.recipefeed.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.RetrofitObject
import com.example.recipefeed.data.recipe.model.Recipe
import com.example.recipefeed.data.recipe.model.Recipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor() : ViewModel() {
    var recipes = MutableStateFlow<List<Recipe>>(listOf())

    init {
        getAllRecipes()
    }

    private fun getAllRecipes() {
        viewModelScope.launch {

            try {
                recipes.value = RetrofitObject.api.getAll()
                Log.d("debug",recipes.value.toString())
            } catch (e: Exception) {
                Log.e("err",e.toString())
            }
        }
    }
    fun fetch(){
        getAllRecipes()
    }



    suspend fun getById(id: Int): Recipe? {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext RetrofitObject.api.getById(id)
            } catch (e: Exception) {
                null
            }
        }
    }
}
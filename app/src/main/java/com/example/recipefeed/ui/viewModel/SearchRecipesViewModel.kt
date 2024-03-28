package com.example.recipefeed.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.RetrofitObject
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor() : ViewModel() {
    var textSearch = MutableStateFlow("")


    private val _searchRecipes = MutableStateFlow<List<Recipe>>(listOf())

    val searchRecipes: StateFlow<List<Recipe>> = _searchRecipes

    fun getAllRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitObject.api.getAll()
                if (response.isSuccessful)
                    _searchRecipes.value = RetrofitObject.api.getAll().body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}
package com.example.recipefeed.screens.mainMenu.searchScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.models.recipe.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) : ViewModel() {
    var textSearch = MutableStateFlow("")


    private val _searchRecipes = MutableStateFlow<List<Recipe>>(listOf())

    val searchRecipes: StateFlow<List<Recipe>> = _searchRecipes

    fun getAllRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = recipeFeedApi.getAll()
                if (response.isSuccessful)
                    _searchRecipes.value = response.body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}
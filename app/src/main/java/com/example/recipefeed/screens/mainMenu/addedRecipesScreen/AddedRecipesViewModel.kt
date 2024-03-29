package com.example.recipefeed.screens.mainMenu.addedRecipesScreen

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
class AddedRecipesViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) : ViewModel() {
    private val _addedRecipes = MutableStateFlow<List<Recipe>>(listOf())

    val addedRecipes: StateFlow<List<Recipe>> = _addedRecipes

    fun getAllRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = recipeFeedApi.getAll()
                if (response.isSuccessful)
                    _addedRecipes.value = response.body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}
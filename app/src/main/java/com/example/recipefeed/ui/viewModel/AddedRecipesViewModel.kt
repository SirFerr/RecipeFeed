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
class AddedRecipesViewModel @Inject constructor() : ViewModel() {
    private val _addedRecipes = MutableStateFlow<List<Recipe>>(listOf())

    val addedRecipes: StateFlow<List<Recipe>> = _addedRecipes

    fun getAllRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitObject.api.getAll()
                if (response.isSuccessful)
                    _addedRecipes.value = RetrofitObject.api.getAll().body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}
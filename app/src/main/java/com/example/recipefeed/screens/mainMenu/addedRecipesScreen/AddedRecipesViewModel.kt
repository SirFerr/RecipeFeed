package com.example.recipefeed.screens.mainMenu.addedRecipesScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var isLoading = MutableStateFlow(true)

    val isSuccessful = MutableStateFlow(true)


    val recipes =
        MutableStateFlow<List<Recipe>>(listOf())

    init {
        getAllRecipes()
    }

    fun getAllRecipes() {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = recipeFeedApi.getAll()
                isSuccessful.value = response.isSuccessful
                Log.d("response", response.isSuccessful.toString())
                if (response.isSuccessful)
                    recipes.value = response.body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
                isSuccessful.value = false
            }
            finally {
                isLoading.value = false
            }
        }
    }
}
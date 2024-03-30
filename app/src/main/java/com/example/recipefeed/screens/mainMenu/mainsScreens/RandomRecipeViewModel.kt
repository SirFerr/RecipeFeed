package com.example.recipefeed.screens.mainMenu.mainsScreens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.recipe.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomRecipeViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) :
    ViewModel() {

    private val _randomRecipe = MutableStateFlow(Recipe())

    val randomRecipe: StateFlow<Recipe> = _randomRecipe

    init {
        getRandomRecipe()
        Log.d("RandomRecipeViewModel", this.toString())
    }

    fun getRandomRecipe() {
        Log.d("RandomRecipeViewModel +", this.toString())
        viewModelScope.launch {

            try {
                val response = recipeFeedApi.getById(kotlin.random.Random.nextInt(1, 120))
                if (response.isSuccessful)
                    _randomRecipe.value = response.body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}
package com.example.recipefeed.screens.mainMenu.mainsScreens

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
class MainScreenViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) :
    ViewModel() {


    val randomRecipe = MutableStateFlow(Recipe())
    val isSuccessful = MutableStateFlow(true)

    init {
        getRandomRecipe()
        Log.d("RandomRecipeViewModel", this.toString())
    }

    fun getRandomRecipe() {
        Log.d("RandomRecipeViewModel +", this.toString())
        viewModelScope.launch {
            try {
                var response = recipeFeedApi.getById(kotlin.random.Random.nextInt(1, 400))
                if (response.isSuccessful)
                    randomRecipe.value = response.body()!!
                isSuccessful.value = response.isSuccessful
            } catch (e: Exception) {
                isSuccessful.value = false
                Log.e("err", e.toString())
            }
        }
    }
}
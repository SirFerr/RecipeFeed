package com.example.recipefeed.view.mainMenu.mainsScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MainScreenViewModel @Inject constructor(
    private val repository: Repository

) : ViewModel() {

    val recipe = MutableStateFlow(Recipe())
    val isSuccessful = MutableStateFlow(true)

    init {
        getRandomRecipe()
    }


    fun getRandomRecipe() {
        viewModelScope.launch {
            try {
                val response = repository.getRandomRecipe()
                isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {
                    response.body()?.let {
                        recipe.value = it
                    }
                }
            } catch (e: Exception) {
                isSuccessful.value = false
            }
        }
    }

    fun addToFavourites() {
        viewModelScope.launch {
            try {
                val response = repository.addToFavourites(recipe.value)
                isSuccessful.value = response.isSuccessful
            } catch (e: Exception) {
                isSuccessful.value = false
            }
        }
    }
}
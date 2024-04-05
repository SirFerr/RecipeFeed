package com.example.recipefeed.screens.mainMenu.mainsScreens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.recipe.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) :
    ViewModel() {


    val randomRecipe = MutableStateFlow(Recipe())
    val isSuccessful = MutableStateFlow(true)
    var response = MutableStateFlow(Response.success(listOf<Recipe>()))

    init {
        getResponse()
    }

    fun getResponse() {
        viewModelScope.launch {
            try {
                response.value = recipeFeedApi.getAll()
                isSuccessful.value = response.value.isSuccessful
                getRandomRecipe()
            } catch (e: Exception) {
                isSuccessful.value = false
                Log.e("err", e.toString())
            }
        }
    }

    fun getRandomRecipe() {
        viewModelScope.launch {
            if (response.value.isSuccessful)
                randomRecipe.value = response.value.body()!!.random()

        }
    }
}
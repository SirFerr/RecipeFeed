package com.example.recipefeed.view.mainMenu.mainsScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel

class MainScreenViewModel @Inject constructor(
    private val recipeFeedApi: RecipeFeedApi,
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager
) :
    ViewModel() {


    val recipe = MutableStateFlow(Recipe())
    val isSuccessful = MutableStateFlow(true)
    var response = MutableStateFlow(Response.success(listOf<Recipe>()))
    var isLoading = MutableStateFlow(false)

    init {
        getResponse()
    }

    fun getResponse() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                response.value = recipeFeedApi.getAll(tokenSharedPreferencesManager.getToken())
                isSuccessful.value = response.value.isSuccessful
                getRandomRecipe()
            } catch (e: Exception) {
                isSuccessful.value = false
            }
            isLoading.value = false
        }
    }

    fun getRandomRecipe() {
        viewModelScope.launch {
            if (response.value.isSuccessful)
                recipe.value = response.value.body()!!.random()

        }
    }
}
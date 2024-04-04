package com.example.recipefeed.screens.mainMenu.searchScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.local.SharedPreferencesManager
import com.example.recipefeed.data.models.recipe.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(
    private val recipeFeedApi: RecipeFeedApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) :
    ViewModel() {

    var isLoading = MutableStateFlow(false)
    val searchText = MutableStateFlow("")
    val isSearching = MutableStateFlow(false)
    val isSuccessful = MutableStateFlow(true)
    val isFound = MutableStateFlow(true)
    val recipes = MutableStateFlow<List<Recipe>>(listOf())
    val searchHistory = MutableStateFlow<List<String>>(listOf())

    init {
        searchHistory.value = sharedPreferencesManager.getLastTenStrings()
    }


    fun search() {
        getByName()
        sharedPreferencesManager.saveString(value = searchText.value)
        searchHistory.value = sharedPreferencesManager.getLastTenStrings()
        isSearching.value = false
    }

    fun getByName() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                isLoading.value = true
                val response = recipeFeedApi.getByName(searchText.value)
                isSuccessful.value = response.isSuccessful
                Log.d("response", response.isSuccessful.toString())
                if (response.isSuccessful) {
                    recipes.value = response.body()!!
                    isFound.value = response.body()!! != listOf<Recipe>()
                }
            } catch (e: Exception) {
                Log.e("err", e.toString())
                isSuccessful.value = false
            } finally {
                isLoading.value = false
            }
        }
    }
}
package com.example.recipefeed.view.mainMenu.searchScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.local.SearchHistorySharedPreferencesManager
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(
    private val recipeFeedApi: RecipeFeedApi,
    private val searchHistorySharedPreferencesManager: SearchHistorySharedPreferencesManager,
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager

) :
    ViewModel() {

    val searchText = MutableStateFlow("")


    var isLoading = MutableStateFlow(false)
    val isSearching = MutableStateFlow(false)
    val isSuccessful = MutableStateFlow(true)
    val isFound = MutableStateFlow(true)


    val recipes = MutableStateFlow<List<Recipe>>(listOf())
    val searchHistory = MutableStateFlow<List<String>>(listOf())

    init {
        searchHistory.value = searchHistorySharedPreferencesManager.getLastTenStrings()
//        val timer = Timer("schedule", true)
//        timer.scheduleAtFixedRate(2000, 2000) {
//            search()
//        }
    }


    fun search() {
        if (searchText.value != "") {
            getByName()
            searchHistorySharedPreferencesManager.saveString(value = searchText.value)
        }
        searchHistory.value = searchHistorySharedPreferencesManager.getLastTenStrings()
        isSearching.value = false
    }

    fun getByName() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                isLoading.value = true
                val response = recipeFeedApi.getByName(
                    searchText.value,
                    tokenSharedPreferencesManager.getToken()
                )
                isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {
                    recipes.value = response.body()!!
                    isFound.value = response.body()!! != listOf<Recipe>()
                }
            } catch (e: Exception) {
                isSuccessful.value = false
            } finally {
                isLoading.value = false
            }
        }
    }
}
package com.example.recipefeed.view.mainMenu.searchScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(
    private val repository: Repository
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
        searchHistory.value = repository.getSearchHistory()
//        val timer = Timer("schedule", true)
//        timer.scheduleAtFixedRate(2000, 2000) {
//            search()
//        }
    }


    fun search() {
        if (searchText.value != "") {
            getByName()
            repository.saveRequest(value = searchText.value)
        }
        searchHistory.value = repository.getSearchHistory()
        isSearching.value = false
    }

    fun getByName() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                isLoading.value = true
                val response = repository.getRecipesByName(
                    searchText.value,

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
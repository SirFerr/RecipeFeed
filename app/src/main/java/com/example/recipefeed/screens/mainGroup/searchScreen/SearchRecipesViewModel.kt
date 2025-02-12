package com.example.recipefeed.view.mainMenu.searchScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {


    private var _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private var _isSearching = mutableStateOf(false)
    val isSearching: State<Boolean> = _isSearching
    private var _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful
    private var _isFound = mutableStateOf(false)
    val isFound: State<Boolean> = _isFound


    private var _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    private var _searchHistory = mutableStateOf<List<String>>(emptyList())
    val searchHistory: State<List<String>> = _searchHistory


    val tags = mutableStateOf(
        listOf(
            "1",
            "Vegan",
            "Gluten-Free",
            "Dairy-Free",
            "Nut-Free",
            "Low-Carb",
            "High-Protein",
            "Organic",
            "Non-GMO",
            "Paleo",
            "Keto",
            "Whole30",
            "Pescatarian",
            "Halal",
            "Kosher",
            "Low-Sugar",
            "Low-Sodium",
            "Sugar-Free",
            "Raw",
            "Plant-Based",
            "Fair Trade",
            "Locally Sourced",
            "Sustainable",
            "Seasonal",
            "Farm-To-Table",
            "Spicy",
            "Mild",
            "Medium",
            "Hot",
            "Sweet",
            "Savory",
            "Tangy",
            "Umami",
            "Fusion",
            "Comfort Food",
            "Street Food",
            "Gourmet",
            "Fast Food",
            "Healthy",
            "Quick & Easy",
            "Kid-Friendly",
            "Family-Style",
            "Party Food",
            "Finger Food",
            "BBQ",
            "Grilled",
            "Roasted",
            "Baked",
            "Fried",
            "Steamed",
            "Boiled",
            "Slow-Cooked",
            "Raw",
            "Fresh",
            "Frozen",
            "Canned",
            "Fermented",
            "Pickled",
            "Smoked"
        )
    )

    init {
        _searchHistory.value = repository.getSearchHistory()
//        val timer = Timer("schedule", true)
//        timer.scheduleAtFixedRate(2000, 2000) {
//            search()
//        }
    }


    fun search() {
        if (searchText.value != "") {
            getByName()
            repository.saveRequest(value = searchText.value)
        } else {
            _recipes.value = listOf()
        }
        _searchHistory.value = repository.getSearchHistory()
        _isSearching.value = false
    }

    fun getByName() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _isLoading.value = true
                val response = repository.getRecipesByName(
                    searchText.value,

                    )
                _isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {
                    _recipes.value = response.body()!!
                    _isFound.value = response.body()!! != listOf<Recipe>()
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    //Setters
    fun setSearchText(string: String) {
        _searchText.value = string
    }

    fun setIsSearching(boolean: Boolean? = null) {
        if (boolean != null) {
            _isSearching.value = boolean
        } else
            _isSearching.value = !_isSearching.value
    }


}
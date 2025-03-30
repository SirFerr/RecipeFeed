package com.example.recipefeed.view.mainMenu.searchScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.Recipe
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

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
            "1", "Vegan", "Gluten-Free", "Dairy-Free", "Nut-Free", "Low-Carb", "High-Protein",
            "Organic", "Non-GMO", "Paleo", "Keto", "Whole30", "Pescatarian", "Halal", "Kosher",
            "Low-Sugar", "Low-Sodium", "Sugar-Free", "Raw", "Plant-Based", "Fair Trade",
            "Locally Sourced", "Sustainable", "Seasonal", "Farm-To-Table", "Spicy", "Mild",
            "Medium", "Hot", "Sweet", "Savory", "Tangy", "Umami", "Fusion", "Comfort Food",
            "Street Food", "Gourmet", "Fast Food", "Healthy", "Quick & Easy", "Kid-Friendly",
            "Family-Style", "Party Food", "Finger Food", "BBQ", "Grilled", "Roasted", "Baked",
            "Fried", "Steamed", "Boiled", "Slow-Cooked", "Raw", "Fresh", "Frozen", "Canned",
            "Fermented", "Pickled", "Smoked"
        )
    )

    init {
        viewModelScope.launch {
            _searchHistory.value = repository.getSearchHistory()
        }
    }


    fun search() {
        if (searchText.value.isNotBlank()) {
            getByName()
            viewModelScope.launch {
                repository.saveSearchRequest(searchText.value)
                _searchHistory.value = repository.getSearchHistory()
            }
        } else {
            _recipes.value = emptyList()
        }
        _isSearching.value = false
    }

    fun getByName() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.searchRecipesByName(
                    query = searchText.value,
                    skip = 0,
                    limit = 20
                )
                _isSuccessful.value = result.isSuccess
                if (result.isSuccess) {
                    _recipes.value = result.getOrNull() ?: emptyList()
                    _isFound.value = _recipes.value.isNotEmpty()
                } else {
                    _isSuccessful.value = false
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Setters
    fun setSearchText(string: String) {
        _searchText.value = string
    }

    fun setIsSearching(boolean: Boolean? = null) {
        _isSearching.value = boolean ?: !_isSearching.value
    }
}
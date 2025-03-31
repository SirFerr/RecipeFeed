package com.example.recipefeed.view.mainMenu.searchScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.Recipe
import com.example.recipefeed.data.models.Tag
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

    private var _selectedTag = mutableStateOf<String?>(null)
    val selectedTag: State<String?> = _selectedTag

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

    private var _tags = mutableStateOf<List<Tag>>(emptyList())
    val tags: State<List<Tag>> = _tags

    init {
        viewModelScope.launch {
            _searchHistory.value = repository.getSearchHistory()
            fetchTags()  // Load tags on initialization
        }
    }

    fun search() {
        if (searchText.value.isNotBlank()) {
            getByName()
            viewModelScope.launch {
                repository.saveSearchRequest(searchText.value)
                _searchHistory.value = repository.getSearchHistory()
            }
        } else if (_selectedTag.value != null) {
            getByTag()  // Search by tag if no text but a tag is selected
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

    fun getByTag() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val selectedTagName = _selectedTag.value ?: return@launch
                val tag = _tags.value.find { it.name == selectedTagName }
                if (tag != null) {
                    val result = repository.searchRecipesByTags(
                        tagIds = listOf(tag.id),
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
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchTags() {
        viewModelScope.launch {
            try {
                val result = repository.getTagsList(skip = 0, limit = 100)  // Adjust limit as needed
                if (result.isSuccess) {
                    _tags.value = result.getOrNull() ?: emptyList()
                }
            } catch (e: Exception) {
                _tags.value = emptyList()
            }
        }
    }

    // Setters
    fun setSearchText(string: String) {
        _searchText.value = string
        _selectedTag.value = null  // Clear selected tag when typing
    }

    fun setSelectedTag(tag: String) {
        _selectedTag.value = tag
        _searchText.value = ""  // Clear search text when selecting a tag
        search()  // Trigger search immediately
    }

    fun setIsSearching(boolean: Boolean? = null) {
        _isSearching.value = boolean ?: !_isSearching.value
    }
}
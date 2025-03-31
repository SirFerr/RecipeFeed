package com.example.recipefeed.view.mainMenu.searchScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.FavoriteCreate
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

    private var _favoriteStatus = mutableStateOf<Map<Int, Boolean>>(emptyMap())
    val favoriteStatus: State<Map<Int, Boolean>> = _favoriteStatus

    init {
        viewModelScope.launch {
            _searchHistory.value = repository.getSearchHistory()
            fetchTags()
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
            getByTag()
        } else {
            _recipes.value = emptyList()
            _favoriteStatus.value = emptyMap()
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
                    val recipesList = result.getOrNull() ?: emptyList()
                    _recipes.value = recipesList
                    _isFound.value = recipesList.isNotEmpty()
                    checkFavoriteStatus(recipesList)
                } else {
                    _isSuccessful.value = false
                    _favoriteStatus.value = emptyMap()
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
                _favoriteStatus.value = emptyMap()
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
                        val recipesList = result.getOrNull() ?: emptyList()
                        _recipes.value = recipesList
                        _isFound.value = recipesList.isNotEmpty()
                        checkFavoriteStatus(recipesList)
                    } else {
                        _isSuccessful.value = false
                        _favoriteStatus.value = emptyMap()
                    }
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
                _favoriteStatus.value = emptyMap()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchTags() {
        viewModelScope.launch {
            try {
                val result = repository.getTagsList(skip = 0, limit = 100)
                if (result.isSuccess) {
                    _tags.value = result.getOrNull() ?: emptyList()
                }
            } catch (e: Exception) {
                _tags.value = emptyList()
            }
        }
    }

    private suspend fun checkFavoriteStatus(recipes: List<Recipe>) {
        val statusMap = mutableMapOf<Int, Boolean>()
        recipes.forEach { recipe ->
            val isFavoriteResult = repository.isRecipeFavorite(recipe.id)
            statusMap[recipe.id] = isFavoriteResult.getOrNull() ?: false
        }
        _favoriteStatus.value = statusMap
    }

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            try {
                val currentUserResult = repository.getCurrentUser()
                if (currentUserResult.isSuccess) {
                    val userId = currentUserResult.getOrNull()?.id ?: return@launch
                    val isFavorite = _favoriteStatus.value[recipeId] ?: false
                    val result = if (isFavorite) {
                        repository.deleteFavorite(recipeId)
                    } else {
                        repository.addFavorite(FavoriteCreate(userId = userId, recipeId = recipeId))
                    }
                    if (result.isSuccess) {
                        _favoriteStatus.value = _favoriteStatus.value.toMutableMap().apply {
                            this[recipeId] = !isFavorite
                        }
                    }
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun setSearchText(string: String) {
        _searchText.value = string
        _selectedTag.value = null
    }

    fun setSelectedTag(tag: String) {
        _selectedTag.value = tag
        _searchText.value = ""
        search()
    }

    fun setIsSearching(boolean: Boolean? = null) {
        _isSearching.value = boolean ?: !_isSearching.value
    }
}
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

    private var _selectedTags = mutableStateOf<List<String>>(emptyList()) // Изменено на список
    val selectedTags: State<List<String>> = _selectedTags

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private var _isSearching = mutableStateOf(false)
    val isSearching: State<Boolean> = _isSearching

    private var _isSuccessful = mutableStateOf(true)
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

    private var _translatedTags = mutableStateOf<Map<String, String>>(emptyMap())
    val translatedTags: State<Map<String, String>> = _translatedTags


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
        } else if (_selectedTags.value.isNotEmpty()) {
            getByTags()
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
                    val recipesList = result.getOrNull()?.map { it.translateToRussian() } ?: emptyList() // ⬅️ Перевод
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


    fun getByTags() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val tagIds = _tags.value.filter { _selectedTags.value.contains(it.name) }.map { it.id }
                if (tagIds.isNotEmpty()) {
                    val result = repository.searchRecipesByTags(tagIds, 0, 20)
                    _isSuccessful.value = result.isSuccess
                    if (result.isSuccess) {
                        val recipesList = result.getOrNull()?.map { it.translateToRussian() } ?: emptyList() // ⬅️ Перевод
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
                    val tags = result.getOrNull() ?: emptyList()
                    _tags.value = tags
                    val translated = tags.associate { it.name to it.translateToRussian().name }
                    _translatedTags.value = translated
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
        _selectedTags.value = emptyList() // Очищаем теги при вводе текста
    }

    fun addSelectedTag(tag: String) {
        if (!_selectedTags.value.contains(tag)) {
            _selectedTags.value = _selectedTags.value + tag
            search() // Автоматический поиск после добавления тега
        }
    }

    fun removeSelectedTag(tag: String) {
        _selectedTags.value = _selectedTags.value.filter { it != tag }
        if (_selectedTags.value.isNotEmpty()) {
            search() // Обновляем поиск, если остались теги
        } else {
            _recipes.value = emptyList()
            _favoriteStatus.value = emptyMap()
        }
    }

    fun setIsSearching(boolean: Boolean? = null) {
        _isSearching.value = boolean ?: !_isSearching.value
    }
}
package com.example.recipefeed.view.mainMenu.recipeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.local.RoleSharedPreferencesManager
import com.example.recipefeed.data.models.FavoriteCreate
import com.example.recipefeed.data.models.Nutrition
import com.example.recipefeed.data.models.Recipe
import com.example.recipefeed.data.models.RecipeIngredient
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val roleSharedPreferencesManager: RoleSharedPreferencesManager
) : ViewModel() {

    private var _recipe = mutableStateOf<Recipe?>(null)
    val recipe: State<Recipe?> = _recipe

    private var _isModerator = mutableStateOf(false)
    val isModerator: State<Boolean> = _isModerator

    private var _isApproveShow = mutableStateOf(false)
    val isApproveShow: State<Boolean> = _isApproveShow

    private var _isRejectShow = mutableStateOf(false)
    val isRejectShow: State<Boolean> = _isRejectShow

    private var _rejectReason = mutableStateOf("")
    val rejectReason: State<String> = _rejectReason

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private var _isLiked = mutableStateOf(false)
    val isLiked: State<Boolean> = _isLiked

    private var _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    private var _ingredients = mutableStateOf<List<RecipeIngredient>>(emptyList())
    val ingredients: State<List<RecipeIngredient>> = _ingredients

    private var _nutrition = mutableStateOf<Nutrition?>(null)
    val nutrition: State<Nutrition?> = _nutrition

    init {
        getIsModerator()
    }

    fun setRejectReason(string: String) {
        _rejectReason.value = string
    }

    fun setIsApproveShow(boolean: Boolean) {
        _isApproveShow.value = boolean
    }

    fun setIsRejectShow(boolean: Boolean) {
        _isRejectShow.value = boolean
    }

    fun toggleLike() {
        viewModelScope.launch {
            try {
                val currentUserResult = repository.getCurrentUser()
                if (currentUserResult.isSuccess) {
                    val userId = currentUserResult.getOrNull()?.id ?: return@launch
                    val recipeId = _recipe.value?.id ?: return@launch
                    val isFavorite = _isLiked.value
                    val result = if (isFavorite) {
                        repository.deleteFavorite(recipeId)
                    } else {
                        repository.addFavorite(FavoriteCreate(userId = userId, recipeId = recipeId))
                    }
                    if (result.isSuccess) {
                        _isLiked.value = !isFavorite
                        _recipe.value = _recipe.value?.copy(
                            likes = if (!isFavorite) recipe.value!!.likes + 1 else recipe.value!!.likes - 1
                        )
                        _isSuccessful.value = true
                    }
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun getIsModerator() {
        _isModerator.value = roleSharedPreferencesManager.isModerator()
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getRecipeById(id)
                _isSuccessful.value = result.isSuccess
                if (result.isSuccess) {
                    _recipe.value = result.getOrNull()
                    checkIfLiked()
                    loadIngredients(id)
                    loadNutrition(id)
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun checkIfLiked() {
        viewModelScope.launch {
            try {
                val recipeId = _recipe.value?.id ?: return@launch
                val isFavoriteResult = repository.isRecipeFavorite(recipeId)
                _isLiked.value = isFavoriteResult.getOrNull() ?: false
            } catch (e: Exception) {
                _isLiked.value = false
            }
        }
    }

    private fun loadIngredients(recipeId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getRecipeIngredients(recipeId)
                if (result.isSuccess) {
                    _ingredients.value = result.getOrNull() ?: emptyList()
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    private fun loadNutrition(recipeId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.calculateNutrition(recipeId)
                if (result.isSuccess) {
                    _nutrition.value = result.getOrNull()
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun approve() {
        viewModelScope.launch {
            try {
                _recipe.value?.id?.let { recipeId ->
                    val result = repository.approveRecipe(recipeId)
                    if (result.isSuccess) {
                        _recipe.value = result.getOrNull()
                        _isApproveShow.value = false
                    }
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun reject() {
        if (_rejectReason.value.isNotBlank()) {
            viewModelScope.launch {
                try {
                    _recipe.value?.id?.let { recipeId ->
                        val result = repository.rejectRecipe(recipeId, _rejectReason.value)
                        if (result.isSuccess) {
                            _recipe.value = result.getOrNull()
                            _isRejectShow.value = false
                            _rejectReason.value = ""
                        }
                    }
                } catch (e: Exception) {
                    // Обработка ошибки
                }
            }
        }
    }
}
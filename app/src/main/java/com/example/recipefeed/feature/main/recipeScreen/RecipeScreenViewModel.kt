package com.example.recipefeed.view.mainMenu.recipeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.local.RoleSharedPreferencesManager
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val repository: Repository,
    private val roleSharedPreferencesManager: RoleSharedPreferencesManager
) :
    ViewModel() {
    private var _recipe = mutableStateOf(Recipe())
    val recipe: State<Recipe> = _recipe

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

    fun setRejectReason(string: String) {
        _rejectReason.value = string
    }

    fun setIsApproveShow(boolean: Boolean) {
        _isApproveShow.value = boolean
    }

    fun setIsRejectShow(boolean: Boolean) {
        _isRejectShow.value = boolean
    }

    fun changeLike() {
        _isLiked.value = !_isLiked.value
    }

    fun approve() {
        _isApproveShow.value = false
    }

    fun reject() {
        if (!_rejectReason.value.isEmpty()) {

            _isRejectShow.value = false
        }
    }

    fun getIsModerator() {
        _isModerator.value = roleSharedPreferencesManager.isModerator()
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response =
                    repository.getRecipeById(id)
                if (response.isSuccessful)
                    _recipe.value = response.body()!!
            } catch (e: Exception) {
            }
            _isLoading.value = false
        }
    }

    fun addToFavourites() {
        viewModelScope.launch {
            try {
                val response = repository.addToFavourites(recipe.value)
                _isSuccessful.value = response.isSuccessful
            } catch (e: Exception) {
                _isSuccessful.value = false
            }
        }
    }
}
package com.example.recipefeed.view.mainMenu.mainsScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Loading)
    val mainState: StateFlow<MainState> = _mainState.asStateFlow()

    private val _nextRecipe = MutableStateFlow<Recipe>(Recipe())
    val nextRecipe: StateFlow<Recipe> = _nextRecipe.asStateFlow()

    init {
        fetchRandomRecipe(true)
        fetchRandomRecipe(false)
    }

    fun onSwipeLeft() {
        _mainState.value = MainState.Success(nextRecipe.value)
        fetchRandomRecipe(false)
    }

    fun onSwipeRight() {
        _mainState.value = MainState.Success(nextRecipe.value)
        fetchRandomRecipe(false)
    }

    private fun fetchRandomRecipe(isInitial: Boolean) {
        viewModelScope.launch {
            try {
                val response = repository.getRandomRecipe()
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (isInitial) {
                            _mainState.value = MainState.Success(it)
                        } else {
                            _nextRecipe.value = it
                        }
                    }
                } else {
                    _mainState.value = MainState.Error(response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                _mainState.value = MainState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addToFavourites() {
        viewModelScope.launch {
            try {
                if (mainState.value is MainState.Success) {
                    repository.addToFavourites((mainState.value as MainState.Success).recipe)
                } else {
                    fetchRandomRecipe(true)
                }
            } catch (e: Exception) {
                // Handle exception if needed
            }
        }
    }
}


sealed class MainState {
    object Loading : MainState()
    data class Success(val recipe: Recipe) : MainState()
    data class Error(val message: String) : MainState()
}

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

    init {
        getRandomRecipe()
    }


    fun getRandomRecipe() {
        _mainState.value=MainState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getRandomRecipe()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _mainState.value = MainState.Success(it)
                    }
                } else {
                    _mainState.value = MainState.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                _mainState.value = MainState.Error(e.message.toString())
            }
        }
    }

    fun addToFavourites() {
        viewModelScope.launch {
            try {
                if(mainState.value is MainState.Success){
                    repository.addToFavourites(
                        (mainState.value as MainState.Success).recipe
                    )
                }
                else{
                    getRandomRecipe()
                }
            } catch (e: Exception) {
            }
        }
    }
}

sealed class MainState {
    object Loading : MainState()
    data class Success(val recipe: Recipe) : MainState()
    data class Error(val message: String) : MainState()
}

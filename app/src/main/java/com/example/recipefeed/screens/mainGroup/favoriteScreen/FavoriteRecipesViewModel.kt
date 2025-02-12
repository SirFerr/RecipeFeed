package com.example.recipefeed.view.mainMenu.favoriteScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(
    private val repository: Repository

) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    private var _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    init {
        getFavouritesRecipes()

    }

    fun getFavouritesRecipes() {
        _isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getFavouritesRecipes()
                _isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {
                    _recipes.value = response.body()!!.map { it.recipe }
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
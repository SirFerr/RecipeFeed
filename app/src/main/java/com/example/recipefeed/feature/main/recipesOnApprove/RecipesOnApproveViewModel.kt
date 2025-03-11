package com.example.recipefeed.feature.main.recipesOnApprove

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesOnApproveViewModel @Inject constructor(
) :
    ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    private var _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    init {
        getRecipesOnApprove()
    }

    fun getRecipesOnApprove() {
        _isLoading.value = true
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = repository.getUsersRecipes()
//                _isSuccessful.value = response.isSuccessful
//                if (response.isSuccessful)
//                    _recipes.value = response.body()!!
//            } catch (e: Exception) {
//                _isSuccessful.value = false
//            } finally {
//                _isLoading.value = false
//            }
//        }
    }
}
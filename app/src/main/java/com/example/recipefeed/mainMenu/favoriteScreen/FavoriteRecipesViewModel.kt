package com.example.recipefeed.view.mainMenu.favoriteScreen

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

    var isLoading = MutableStateFlow(true)

    val isSuccessful = MutableStateFlow(true)


    val recipes =
        MutableStateFlow<List<Recipe>>(listOf())

    init {
        getFavouritesRecipes()

    }

    fun getFavouritesRecipes() {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getFavouritesRecipes()
                isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {
                    recipes.value = response.body()!!.map { it.recipe }
                }
            } catch (e: Exception) {
                isSuccessful.value = false
            } finally {
                isLoading.value = false
            }
        }
    }
}
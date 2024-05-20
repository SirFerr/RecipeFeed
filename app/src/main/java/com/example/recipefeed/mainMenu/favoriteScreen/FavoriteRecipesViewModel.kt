package com.example.recipefeed.view.mainMenu.favoriteScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(
    private val recipeFeedApi: RecipeFeedApi,
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager
) : ViewModel() {

    var isLoading = MutableStateFlow(true)

    val isSuccessful = MutableStateFlow(true)


    val recipes =
        MutableStateFlow<List<Recipe>>(listOf())

    init {
        getAllRecipes()

    }

    fun getAllRecipes() {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = recipeFeedApi.getAll(token = tokenSharedPreferencesManager.getToken())
                isSuccessful.value = response.isSuccessful
                if (response.isSuccessful)
                    recipes.value = response.body()!!
            } catch (e: Exception) {
                isSuccessful.value = false
            } finally {
                isLoading.value = false
            }
        }
    }
}
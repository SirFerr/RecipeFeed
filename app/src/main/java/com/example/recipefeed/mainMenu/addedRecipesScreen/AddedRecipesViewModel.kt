package com.example.recipefeed.view.mainMenu.addedRecipesScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddedRecipesViewModel @Inject constructor(
    private val repository: Repository

) : ViewModel() {
    var isLoading = MutableStateFlow(true)

    val isSuccessful = MutableStateFlow(true)


    val recipes =
        MutableStateFlow<List<Recipe>>(listOf())

    init {
        getAddedRecipes()
    }

    fun getAddedRecipes() {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getUsersRecipes()
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
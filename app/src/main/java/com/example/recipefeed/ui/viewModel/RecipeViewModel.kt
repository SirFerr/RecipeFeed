package com.example.recipefeed.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.RetrofitObject
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor() : ViewModel() {
    var recipes = MutableStateFlow<List<Recipe>>(listOf())
    var randomRecipe = MutableStateFlow(Recipe())

    init {
        getAllRecipes()
        getRandomRecipe()
    }

    private fun getAllRecipes() {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                recipes.value = RetrofitObject.api.getAll()
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }

    fun fetch() {
        getAllRecipes()
    }


    suspend fun getById(id: Int): Recipe? {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext RetrofitObject.api.getById(id)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getRandomRecipe() {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                randomRecipe.value =
                    RetrofitObject.api.getById(kotlin.random.Random.nextInt(1, 100))
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }

    fun addRecipes(recipe: Recipe, imagePart: MultipartBody.Part?) {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                imagePart?.let { RetrofitObject.api.addRecipe(recipe, it) }
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}


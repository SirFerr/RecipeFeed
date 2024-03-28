package com.example.recipefeed.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.RetrofitObject
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor() : ViewModel() {
    var textSearch = MutableStateFlow("")

    private val _recipes = MutableStateFlow<List<Recipe>>(listOf())

    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _randomRecipe = MutableStateFlow(Recipe())

    val randomRecipe: StateFlow<Recipe> = _randomRecipe

    private val _idRecipe = MutableStateFlow(Recipe())

    val idRecipe: StateFlow<Recipe> = _idRecipe

    init {
        Log.d("recipeViewModel", this.toString())
    }

    fun getAllRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitObject.api.getAll()
                if (response.isSuccessful)
                    _recipes.value = RetrofitObject.api.getAll().body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }


    fun getById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = RetrofitObject.api.getById(id)
                if (response.isSuccessful)
                    _idRecipe.value = response.body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }

    fun getRandomRecipe() {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = RetrofitObject.api.getById(kotlin.random.Random.nextInt(1, 100))
                if (response.isSuccessful)
                    _randomRecipe.value = response.body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }

    fun addRecipes(recipe: Recipe, imagePart: MultipartBody.Part?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = imagePart?.let { RetrofitObject.api.addRecipe(recipe, it) }
                if (response?.isSuccessful == true) {

                }
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}


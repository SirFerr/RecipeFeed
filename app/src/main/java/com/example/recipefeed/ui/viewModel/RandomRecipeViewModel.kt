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
import javax.inject.Inject

@HiltViewModel
class RandomRecipeViewModel @Inject constructor() : ViewModel() {

    private val _randomRecipe = MutableStateFlow(Recipe())

    val randomRecipe: StateFlow<Recipe> = _randomRecipe


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
}
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
class IdRecipeViewModel @Inject constructor() : ViewModel() {
    private val _idRecipe = MutableStateFlow(Recipe())

    val idRecipe: StateFlow<Recipe> = _idRecipe

    fun getById(id: Int) {
        Log.d("getByID",id.toString())
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
}
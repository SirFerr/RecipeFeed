package com.example.recipefeed.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.RetrofitObject
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class OtherNetworkViewModel @Inject constructor() : ViewModel() {
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
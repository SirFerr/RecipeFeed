package com.example.recipefeed.view.mainMenu.editRecipeScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EditRecipeScreenViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {
    val recipe = MutableStateFlow(Recipe())

    fun getById(id: Int) {
        viewModelScope.launch {

            try {
                val response = repository.getRecipeById(id)
                if (response.isSuccessful)
                    recipe.value = response.body()!!
            } catch (e: Exception) {
            }
        }
    }

    fun editRecipe(recipe: Recipe, imagePart: MultipartBody.Part?, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = imagePart?.let { repository.updateRecipe(recipe.id, recipe, it,) }
                if (response?.isSuccessful == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
            }
        }
    }

    fun deleteRecipeById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.deleteRecipeById(id)
                Log.d("delete", response.isSuccessful.toString())
            } catch (e: Exception) {

            }
        }
    }
}
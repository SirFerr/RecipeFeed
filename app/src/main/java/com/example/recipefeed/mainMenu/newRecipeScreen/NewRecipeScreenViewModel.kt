package com.example.recipefeed.view.mainMenu.newRecipeScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class NewRecipeScreenViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {

    fun addRecipes(recipe: Recipe, imagePart: MultipartBody.Part?, context: Context) {
        viewModelScope.launch {
            try {
                val response = imagePart?.let {
                    repository.addRecipe(
                        recipe,
                        it,
                    )
                }
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
}
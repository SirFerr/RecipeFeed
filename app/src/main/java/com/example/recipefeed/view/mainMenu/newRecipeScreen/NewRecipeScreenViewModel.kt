package com.example.recipefeed.view.mainMenu.newRecipeScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.remote.recipe.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class NewRecipeScreenViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) : ViewModel() {


    fun addRecipes(recipe: Recipe, imagePart: MultipartBody.Part?, context: Context) {
        viewModelScope.launch {
            try {
                val response = imagePart?.let { recipeFeedApi.addRecipe(recipe, it) }
                if (response?.isSuccessful == true) {
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
            }
        }
    }
}
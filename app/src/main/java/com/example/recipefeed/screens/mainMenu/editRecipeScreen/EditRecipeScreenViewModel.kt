package com.example.recipefeed.screens.mainMenu.editRecipeScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.recipe.Recipe
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EditRecipeScreenViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) : ViewModel() {
    private val _idRecipe = MutableStateFlow(Recipe())

    val idRecipe: StateFlow<Recipe> = _idRecipe

    fun getById(id: Int) {
        Log.d("getByID",id.toString())
        viewModelScope.launch {

            try {
                val response = recipeFeedApi.getById(id)
                if (response.isSuccessful)
                    _idRecipe.value = response.body()!!
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }

    fun addRecipes(recipe: Recipe, imagePart: MultipartBody.Part?, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = imagePart?.let { recipeFeedApi.addRecipe(recipe, it) }
                if (response?.isSuccessful == true) {
                    Log.d("Successful", response.code().toString())
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("err", response?.code().toString())
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }
}
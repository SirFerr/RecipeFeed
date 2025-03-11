package com.example.recipefeed.view.mainMenu.editRecipeScreen

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.feature.main.accountScreen.convertToMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@HiltViewModel
class EditRecipeScreenViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context

) :
    ViewModel() {
    private var _recipe = mutableStateOf(Recipe())
    val recipe: State<Recipe> = _recipe


    private var _recipeName = mutableStateOf("")
    val recipeName: State<String> = _recipeName
    private var _description = mutableStateOf("")
    val description: State<String> = _description
    private var _ingredients = mutableStateOf("")
    val ingredients: State<String> = _ingredients
    private var _timeToCook = mutableStateOf("")
    val timeToCook: State<String> = _timeToCook
    private var _isDelete = mutableStateOf(false)
    val isDelete: State<Boolean> = _isDelete

    private var _selectImages = mutableStateOf<Any?>(null)
    val selectImages: State<Any?> = _selectImages



    fun getById(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRecipeById(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _recipe.value = it
                        _recipeName.value = it.recipeName
                        _description.value = it.description
                        _ingredients.value = it.ingredients
                        _timeToCook.value = it.timeToCook
                        val imageBytes = Base64.decode(recipe.value.imageData)
                        _selectImages.value =
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun editRecipe() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = convertToMultipart(selectImages.value, context)?.let {
                    repository.updateRecipe(
                        recipe.value.id, Recipe(
                            recipeName = recipeName.value,
                            description = description.value,
                            timeToCook = timeToCook.value,
                            ingredients = ingredients.value
                        ),
                        it
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

    fun deleteRecipeById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.deleteRecipeById(id)
                Log.d("delete", response.isSuccessful.toString())
            } catch (e: Exception) {

            }
        }
    }

    //Setters
    fun setRecipeName(string: String) {
        _recipeName.value = string
    }

    fun setDescription(string: String) {
        _description.value = string
    }

    fun setIngredients(string: String) {
        _ingredients.value = string
    }

    fun setTimeToCook(string: String) {
        _timeToCook.value = string
    }

    fun setSelectImages(any: Any?) {
        _selectImages.value = any
    }

    fun changeIsDelete() {
        _isDelete.value = !_isDelete.value
    }
}
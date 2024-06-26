package com.example.recipefeed.view.mainMenu.editRecipeScreen

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.screens.mainGroup.accountScreen.recipeEdit.convertToMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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
    val recipe = MutableStateFlow(Recipe())

    val recipeName = MutableStateFlow("")
    val description = MutableStateFlow("")
    val ingredients = MutableStateFlow("")
    val timeToCook = MutableStateFlow("")
    val isDelete = MutableStateFlow(false)

    var selectImages = MutableStateFlow<Any?>(null)


    fun getById(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRecipeById(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        recipe.value = it
                        recipeName.value = it.recipeName
                        description.value = it.description
                        ingredients.value = it.ingredients
                        timeToCook.value = it.timeToCook
                        val imageBytes = Base64.decode(recipe.value.imageData)
                        selectImages.value =
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
        recipeName.value = string
    }

    fun setDescription(string: String) {
        description.value = string
    }

    fun setIngredients(string: String) {
        ingredients.value = string
    }

    fun setTimeToCook(string: String) {
        timeToCook.value = string
    }

    fun setSelectImages(any: Any?) {
        selectImages.value = any
    }

    fun changeIsDelete() {
        isDelete.value = !isDelete.value
    }
}
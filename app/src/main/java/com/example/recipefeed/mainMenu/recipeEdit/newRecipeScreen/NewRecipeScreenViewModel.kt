package com.example.recipefeed.view.mainMenu.newRecipeScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.utils.convertToMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class NewRecipeScreenViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {

    val recipeName = MutableStateFlow("")
    val description = MutableStateFlow("")
    val ingredients = MutableStateFlow("")
    val timeToCook = MutableStateFlow("")

    var selectImages = MutableStateFlow<Any?>(null)


    fun addRecipes(context: Context) {
        viewModelScope.launch {
            try {
                val response = convertToMultipart(selectImages.value, context)?.let {
                    repository.addRecipe(
                        Recipe(
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


}
package com.example.recipefeed.view.mainMenu.newRecipeScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.screens.mainGroup.accountScreen.recipeEdit.convertToMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class NewRecipeScreenViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context
) :
    ViewModel() {

    private var _recipeName = mutableStateOf("")
    val recipeName: State<String> = _recipeName
    private var _description = mutableStateOf("")
    val description: State<String> = _description
    private var _ingredients = mutableStateOf("")
    val ingredients: State<String> = _ingredients
    private var _timeToCook = mutableStateOf("")
    val timeToCook: State<String> = _timeToCook

    private var _selectImages = mutableStateOf<Any?>(null)
    val selectImages: State<Any?> = _selectImages


    fun addRecipes() {
        viewModelScope.launch {
            try {
                val response = convertToMultipart(_selectImages.value, context)?.let {
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


}
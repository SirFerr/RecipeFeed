package com.example.recipefeed.view.mainMenu.newRecipeScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.RecipeIngredientCreate
import com.example.recipefeed.data.repository.RecipeRepository
import com.example.recipefeed.feature.UiIngredient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewRecipeScreenViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _recipeName = mutableStateOf("")
    val recipeName: State<String> = _recipeName

    private var _description = mutableStateOf("")
    val description: State<String> = _description

    private var _ingredients = mutableStateOf<List<UiIngredient>>(emptyList())
    val ingredients: State<List<UiIngredient>> = _ingredients

    private var _steps = mutableStateOf("")
    val steps: State<String> = _steps

    private var _selectedImageFile = mutableStateOf<File?>(null)
    val selectedImageFile: State<File?> = _selectedImageFile

    fun addRecipe() {
        viewModelScope.launch {
            try {
                val recipeResult = repository.createRecipe(
                    name = _recipeName.value,
                    description = _description.value.takeIf { it.isNotBlank() },
                    steps = _steps.value.takeIf { it.isNotBlank() },
                    imageFile = _selectedImageFile.value
                )
                if (recipeResult.isSuccess) {
                    val recipe = recipeResult.getOrNull() ?: return@launch
                    val ingredientsList = _ingredients.value.mapNotNull { uiIngredient ->
                        if (uiIngredient.name.isNotBlank() && uiIngredient.amount != null) {
                            // Предполагаем, что ingredientId будет получен позже
                            RecipeIngredientCreate(
                                recipeId = recipe.id,
                                ingredientId = 0, // Нужно заменить на реальный ID
                                amount = uiIngredient.amount
                            )
                        } else null
                    }
                    if (ingredientsList.isNotEmpty()) {
                        repository.updateRecipeIngredients(recipe.id, ingredientsList)
                    }
                    Toast.makeText(context, "Recipe created successfully", Toast.LENGTH_SHORT)
                        .show()
                    _recipeName.value = ""
                    _description.value = ""
                    _steps.value = ""
                    _ingredients.value = emptyList()
                    _selectedImageFile.value = null
                } else {
                    Toast.makeText(context, "Error creating recipe", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Setters
    fun setRecipeName(string: String) {
        _recipeName.value = string
    }

    fun setDescription(string: String) {
        _description.value = string
    }

    fun addIngredient() {
        _ingredients.value = _ingredients.value + UiIngredient()
    }

    fun deleteIngredient(index: Int) {
        _ingredients.value = _ingredients.value.toMutableList().apply {
            removeAt(index)
        }
    }

    fun changeIngredient(index: Int, ingredient: UiIngredient) {
        _ingredients.value = _ingredients.value.toMutableList().also {
            it[index] = ingredient
        }
    }

    fun setSteps(string: String) {
        _steps.value = string
    }

    fun setSelectedImageFile(file: File?) {
        _selectedImageFile.value = file
    }
}
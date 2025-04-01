package com.example.recipefeed.view.mainMenu.newRecipeScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.Ingredient
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

    private var _availableIngredients = mutableStateOf<List<Ingredient>>(emptyList())
    val availableIngredients: State<List<Ingredient>> = _availableIngredients

    init {
        loadAvailableIngredients()
    }

    private fun loadAvailableIngredients() {
        viewModelScope.launch {
            try {
                val result = repository.getIngredientsList(
                    skip = 0,
                    limit = 100
                )
                if (result.isSuccess) {
                    _availableIngredients.value = result.getOrNull() ?: emptyList()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Error loading ingredients: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun addRecipe() {
        if (!validateFields()) {
            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                val recipeResult = repository.createRecipe(
                    name = _recipeName.value,
                    description = _description.value,
                    steps = _steps.value,
                    imageFile = _selectedImageFile.value
                )
                if (recipeResult.isSuccess) {
                    val recipe = recipeResult.getOrNull() ?: return@launch
                    val ingredientsList = _ingredients.value.mapNotNull { uiIngredient ->
                        val matchingIngredient =
                            _availableIngredients.value.find { it.name == uiIngredient.name }
                        if (uiIngredient.name.isNotBlank() && uiIngredient.amount != null) {
                            RecipeIngredientCreate(
                                recipeId = recipe.id,
                                ingredientId = matchingIngredient?.id ?: 0,
                                amount = uiIngredient.amount
                            )
                        } else null
                    }
                    if (ingredientsList.isNotEmpty()) {
                        repository.updateRecipeIngredients(recipe.id, ingredientsList)
                    }
                    Toast.makeText(context, "Recipe created successfully", Toast.LENGTH_SHORT)
                        .show()
                    clearFields()
                } else {
                    Toast.makeText(context, "Error creating recipe", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(): Boolean {
        // Check if recipe name is not blank
        if (_recipeName.value.isBlank()) return false

        // Check if there is at least one valid ingredient
        val hasValidIngredient = _ingredients.value.any {
            it.name.isNotBlank() && it.amount != null && it.amount > 0
        }
        if (!hasValidIngredient) return false

        // Check if steps are not blank
        if (_steps.value.isBlank()) return false

        return true
    }

    private fun clearFields() {
        _recipeName.value = ""
        _description.value = ""
        _steps.value = ""
        _ingredients.value = emptyList()
        _selectedImageFile.value = null
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
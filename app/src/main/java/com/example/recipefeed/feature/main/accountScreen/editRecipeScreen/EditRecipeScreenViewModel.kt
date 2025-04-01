package com.example.recipefeed.view.mainMenu.editRecipeScreen

import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.Recipe
import com.example.recipefeed.data.models.RecipeIngredient
import com.example.recipefeed.data.models.RecipeIngredientCreate
import com.example.recipefeed.data.repository.RecipeRepository
import com.example.recipefeed.feature.UiIngredient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class EditRecipeScreenViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _recipe = mutableStateOf<Recipe?>(null)
    val recipe: State<Recipe?> = _recipe

    private var _recipeName = mutableStateOf("")
    val recipeName: State<String> = _recipeName

    private var _description = mutableStateOf("")
    val description: State<String> = _description

    private var _ingredients = mutableStateOf<List<UiIngredient>>(emptyList())
    val ingredients: State<List<UiIngredient>> = _ingredients

    private var _steps = mutableStateOf("")
    val steps: State<String> = _steps

    private var _isDelete = mutableStateOf(false)
    val isDelete: State<Boolean> = _isDelete

    private var _selectedImageFile = mutableStateOf<File?>(null)
    val selectedImageFile: State<File?> = _selectedImageFile

    private var _externalIngredients = mutableStateOf<List<Map<String, Any>>>(emptyList())
    val externalIngredients: State<List<Map<String, Any>>> = _externalIngredients

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        searchExternalIngredients("") // Загружаем начальный список внешних ингредиентов
    }

    fun setImageFromBase64(base64String: String) {
        try {
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            val file = File(context.cacheDir, "recipe_image_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { outputStream ->
                outputStream.write(imageBytes)
            }
            _selectedImageFile.value = file
        } catch (e: Exception) {
            Log.e("EditRecipe", "Error decoding image: ${e.message}")
            _selectedImageFile.value = null
        }
    }

    fun searchExternalIngredients(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.getExternalIngredients(query)
                if (result.isSuccess) {
                    _externalIngredients.value = result.getOrNull() ?: emptyList()
                } else {
                    Log.e("EditRecipe", "Failed to load external ingredients: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Log.e("EditRecipe", "Error loading external ingredients: ${e.message}")
            }
        }
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val recipeResult = repository.getRecipeById(id)
                if (recipeResult.isSuccess) {
                    _recipe.value = recipeResult.getOrNull()
                    _recipe.value?.let { recipe ->
                        _recipeName.value = recipe.name
                        _description.value = recipe.description ?: ""
                        _steps.value = recipe.steps ?: ""
                        recipe.imageData?.let { setImageFromBase64(it) }

                        // Загружаем ингредиенты после успешной загрузки рецепта
                        val ingredientsResult = repository.getRecipeIngredients(id)
                        Log.d("EditRecipe", "Recipe ingredients result: $ingredientsResult")
                        if (ingredientsResult.isSuccess) {
                            val recipeIngredients = ingredientsResult.getOrNull() ?: emptyList()
                            Log.d("EditRecipe", "Loaded recipe ingredients: $recipeIngredients")
                            val uiIngredients = recipeIngredients.map { ingredient ->
                                UiIngredient(
                                    name = ingredient.ingredientName,
                                    amount = ingredient.amount,
                                    unit = ingredient.unit,
                                    possibleUnits = _externalIngredients.value
                                        .find { it["name"] == ingredient.ingredientName }
                                        ?.get("possible_units") as? List<String> ?: emptyList()
                                )
                            }
                            _ingredients.value = uiIngredients
                            Log.d("EditRecipe", "Updated UI ingredients: $_ingredients")
                        } else {
                            Toast.makeText(context, "Failed to load ingredients: ${ingredientsResult.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                            Log.e("EditRecipe", "Failed to load ingredients: ${ingredientsResult.exceptionOrNull()?.message}")
                        }
                    }
                } else {
                    Toast.makeText(context, "Failed to load recipe: ${recipeResult.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                    Log.e("EditRecipe", "Failed to load recipe: ${recipeResult.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading recipe: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("EditRecipe", "Error fetching recipe: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun editRecipe() {
        if (!validateFields()) {
            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                val recipeId = _recipe.value?.id ?: return@launch

                val editResult = repository.editRecipe(
                    recipeId = recipeId,
                    name = _recipeName.value,
                    description = _description.value.takeIf { it.isNotBlank() },
                    steps = _steps.value.takeIf { it.isNotBlank() },
                    imageFile = _selectedImageFile.value
                )

                if (editResult.isSuccess) {
                    val ingredientsList = _ingredients.value.mapNotNull { uiIngredient ->
                        if (uiIngredient.name.isNotBlank() && uiIngredient.amount != null && uiIngredient.unit.isNotBlank()) {
                            RecipeIngredientCreate(
                                ingredientName = uiIngredient.name,
                                amount = uiIngredient.amount,
                                unit = uiIngredient.unit
                            )
                        } else null
                    }
                    if (ingredientsList.isNotEmpty()) {
                        repository.updateRecipeIngredients(recipeId, ingredientsList)
                    }
                    Toast.makeText(context, "Recipe updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error updating recipe: ${editResult.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteRecipeById(id: Int) {
        viewModelScope.launch {
            try {
                val deleteResult = repository.deleteRecipe(id)
                if (deleteResult.isSuccess) {
                    Toast.makeText(context, "Recipe deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error deleting recipe", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("EditRecipe", "Error deleting recipe: ${e.message}")
            }
        }
    }

    private fun validateFields(): Boolean {
        if (_recipeName.value.isBlank()) return false
        val hasValidIngredient = _ingredients.value.any {
            it.name.isNotBlank() && it.amount != null && it.amount > 0 && it.unit.isNotBlank()
        }
        if (!hasValidIngredient) return false
        if (_steps.value.isBlank()) return false
        return true
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
        val possibleUnits = _externalIngredients.value.find { it["name"] == ingredient.name }
            ?.get("possible_units") as? List<String> ?: emptyList()
        _ingredients.value = _ingredients.value.toMutableList().also {
            it[index] = ingredient.copy(possibleUnits = possibleUnits)
        }
    }

    fun setSteps(string: String) {
        _steps.value = string
    }

    fun setSelectedImageFile(file: File?) {
        _selectedImageFile.value = file
    }

    fun changeIsDelete() {
        _isDelete.value = !_isDelete.value
    }
}
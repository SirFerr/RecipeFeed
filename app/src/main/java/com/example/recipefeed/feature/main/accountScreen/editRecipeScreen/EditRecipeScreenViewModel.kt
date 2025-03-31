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

    fun setImageFromBase64(base64String: String) {
        try {
            // Декодируем строку Base64 в массив байтов
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)

            // Создаем временный файл в кэше приложения
            val file = File(context.cacheDir, "recipe_image_${System.currentTimeMillis()}.jpg")

            // Записываем байты в файл
            FileOutputStream(file).use { outputStream ->
                outputStream.write(imageBytes)
            }

            // Устанавливаем файл в состояние
            _selectedImageFile.value = file
        } catch (e: Exception) {
            e.printStackTrace()
            _selectedImageFile.value = null // В случае ошибки можно сбросить состояние
        }
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            try {
                val recipeResult = repository.getRecipeById(id)
                if (recipeResult.isSuccess) {
                    _recipe.value = recipeResult.getOrNull()
                    _recipe.value?.let { recipe ->
                        _recipeName.value = recipe.name
                        _description.value = recipe.description ?: ""
                        _steps.value = recipe.steps ?: ""
                        val ingredientsResult = repository.getRecipeIngredients(id)
                        if (ingredientsResult.isSuccess) {
                            _ingredients.value = ingredientsResult.getOrNull()?.map { ingredient ->
                                UiIngredient(
                                    name = "Ingredient ${ingredient.ingredientId}", // Нужно получить имя через getIngredientById
                                    amount = ingredient.amount
                                )
                            } ?: emptyList()
                        }
                        recipe.imageData?.let { setImageFromBase64(it) }
                    }
                }
            } catch (e: Exception) {
                Log.e("EditRecipe", "Error fetching recipe: ${e.message}")
            }
        }
    }

    fun editRecipe() {
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
                        if (uiIngredient.name.isNotBlank() && uiIngredient.amount != null) {
                            RecipeIngredientCreate(
                                recipeId = recipeId,
                                ingredientId = 0, // Нужно заменить на реальный ID
                                amount = uiIngredient.amount
                            )
                        } else null
                    }
                    if (ingredientsList.isNotEmpty()) {
                        repository.updateRecipeIngredients(recipeId, ingredientsList)
                    }
                    Toast.makeText(context, "Recipe updated successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "Error updating recipe " + editResult.toString(), Toast.LENGTH_SHORT).show()
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

    fun changeIsDelete() {
        _isDelete.value = !_isDelete.value
    }
}
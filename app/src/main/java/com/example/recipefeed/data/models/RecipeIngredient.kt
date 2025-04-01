package com.example.recipefeed.data.models

import com.google.gson.annotations.SerializedName

data class RecipeIngredient(
    @SerializedName("recipe_id") val recipeId: Int,
    @SerializedName("ingredient_name") val ingredientName: String, // Теперь используем название ингредиента
    @SerializedName("amount") val amount: Double,
    @SerializedName("unit") val unit: String
)

data class RecipeIngredientCreate(
    @SerializedName("ingredient_name") val ingredientName: String, // Теперь используем название ингредиента
    @SerializedName("amount") val amount: Double,
    @SerializedName("unit") val unit: String
)
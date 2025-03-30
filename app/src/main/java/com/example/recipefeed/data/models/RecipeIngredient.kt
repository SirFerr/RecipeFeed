package com.example.recipefeed.data.models


import com.google.gson.annotations.SerializedName

data class RecipeIngredient(
    @SerializedName("recipe_id") val recipeId: Int,
    @SerializedName("ingredient_id") val ingredientId: Int,
    @SerializedName("amount") val amount: Double
)

data class RecipeIngredientCreate(
    @SerializedName("recipe_id") val recipeId: Int,
    @SerializedName("ingredient_id") val ingredientId: Int,
    @SerializedName("amount") val amount: Double
)
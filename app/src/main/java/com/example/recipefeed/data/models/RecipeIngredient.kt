package com.example.recipefeed.data.models

import com.example.recipefeed.data.api.Translator
import com.google.gson.annotations.SerializedName

data class RecipeIngredient(
    @SerializedName("recipe_id") val recipeId: Int,
    @SerializedName("ingredient_name") val ingredientName: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("unit") val unit: String
) {
    suspend fun translateToRussian(): RecipeIngredient {
        return this.copy(
            ingredientName = Translator.translate(ingredientName),
            unit = Translator.translate(unit)
        )
    }
}

data class RecipeIngredientCreate(
    @SerializedName("ingredient_name") val ingredientName: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("unit") val unit: String
) {
    suspend fun translateToRussian(): RecipeIngredientCreate {
        return this.copy(
            ingredientName = Translator.translate(ingredientName),
            unit = Translator.translate(unit)
        )
    }
}

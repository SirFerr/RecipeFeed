package com.example.recipefeed.data.recipe.model

data class Recipe(
    val id: Int = -1,
    val recipeName: String = "testRecipeName",
    val ingredients: String = "testIngredients",
    val description: String = "testDescription",
    val timeToCook: String = "TestTimeToCook",
    val recipeRating: Float = 0f
)

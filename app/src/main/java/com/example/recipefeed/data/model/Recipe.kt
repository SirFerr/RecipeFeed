package com.example.recipefeed.data.model

data class Recipe(
    val id: Long = 1L,
    val recipeName: String = "testRecipeName",
    val ingredients: String = "testIngredients",
    val description: String = "testDescription",
    val timeToCook: String = "TestTimeToCook",
    val recipeRating: Float = 0f
)

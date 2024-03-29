package com.example.recipefeed.data.models.recipe

import java.util.UUID

data class Recipe(
    val id: Int = -1,
    val recipeName: String = "testRecipeName",
    val ingredients: String = "testIngredients",
    val description: String = "testDescription",
    val timeToCook: String = "TestTimeToCook",
    val recipeLikes: Int = 1,
    val imageData: String = "",
    )

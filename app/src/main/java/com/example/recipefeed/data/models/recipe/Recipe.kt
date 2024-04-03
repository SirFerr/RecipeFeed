package com.example.recipefeed.data.models.recipe

import java.util.UUID

data class Recipe(
    val id: Int = -1,
    val recipeName: String = "",
    val ingredients: String = "",
    val description: String = "",
    val timeToCook: String = "",
    val recipeLikes: Int = 0,
    val imageData: String = "",
    )

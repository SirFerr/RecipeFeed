package com.example.recipefeed.data.remote

import java.util.UUID

data class Recipe(
    val id: Int = -1,
    var recipeName: String = "",
    var ingredients: String = "",
    var description: String = "",
    var timeToCook: String = "",
    var recipeLikes: Int = 0,
    var imageData: String = "",
    )

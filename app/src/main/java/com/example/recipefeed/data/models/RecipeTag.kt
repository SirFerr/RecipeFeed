package com.example.recipefeed.data.models


import com.google.gson.annotations.SerializedName

data class RecipeTag(
    @SerializedName("recipe_id") val recipeId: Int,
    @SerializedName("tag_id") val tagId: Int
)

data class RecipeTagCreate(
    @SerializedName("recipe_id") val recipeId: Int,
    @SerializedName("tag_id") val tagId: Int
)
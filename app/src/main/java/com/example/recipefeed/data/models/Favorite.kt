package com.example.recipefeed.data.models


import com.google.gson.annotations.SerializedName

data class Favorite(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("recipe_id") val recipeId: Int
)

data class FavoriteCreate(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("recipe_id") val recipeId: Int
)
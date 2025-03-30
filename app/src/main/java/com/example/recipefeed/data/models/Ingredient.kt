package com.example.recipefeed.data.models


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("protein") val protein: Double?,
    @SerializedName("carbs") val carbs: Double?,
    @SerializedName("fat") val fat: Double?,
    @SerializedName("calories") val calories: Double?
)

data class IngredientCreate(
    @SerializedName("name") val name: String,
    @SerializedName("protein") val protein: Double?,
    @SerializedName("carbs") val carbs: Double?,
    @SerializedName("fat") val fat: Double?,
    @SerializedName("calories") val calories: Double?
)
package com.example.recipefeed.data.models


import com.google.gson.annotations.SerializedName

data class Nutrition(
    @SerializedName("protein") val protein: Double,
    @SerializedName("carbs") val carbs: Double,
    @SerializedName("fat") val fat: Double,
    @SerializedName("calories") val calories: Double
)
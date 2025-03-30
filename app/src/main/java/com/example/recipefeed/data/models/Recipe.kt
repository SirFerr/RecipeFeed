package com.example.recipefeed.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Recipe(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("add_date") val addDate: Date,
    @SerializedName("steps") val steps: String?,
    @SerializedName("is_on_approve") val isOnApprove: Boolean,
    @SerializedName("reject_reason") val rejectReason: String?,
    @SerializedName("image_data") val imageData: String?, // Base64 строка
    @SerializedName("likes") val likes: Int // Новое поле
)

data class RecipeCreate(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("steps") val steps: String?,
    @SerializedName("image_data") val imageData: String? // Base64 строка
)
package com.example.recipefeed.data.models

import com.example.recipefeed.data.api.Translator
import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("add_date") val addDate: String,
    @SerializedName("steps") val steps: String?,
    @SerializedName("is_on_approve") val isOnApprove: Boolean,
    @SerializedName("reject_reason") val rejectReason: String?,
    @SerializedName("image_data") val imageData: String?,
    @SerializedName("likes") val likes: Int
) {
    suspend fun translateToRussian(): Recipe {
        return this.copy(
            name = Translator.translate(name),
            description = description?.let { Translator.translate(it) },
            steps = steps?.let { Translator.translate(it) },
            rejectReason = rejectReason?.let { Translator.translate(it) }
        )
    }
}

data class RecipeCreate(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("steps") val steps: String?,
    @SerializedName("image_data") val imageData: String?
) {
    suspend fun translateToRussian(): RecipeCreate {
        return this.copy(
            name = Translator.translate(name),
            description = description?.let { Translator.translate(it) },
            steps = steps?.let { Translator.translate(it) }
        )
    }
}

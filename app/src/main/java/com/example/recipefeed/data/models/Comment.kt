package com.example.recipefeed.data.models

import com.example.recipefeed.data.api.Translator
import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("recipe_id") val recipeId: Int,
    @SerializedName("comment_text") val commentText: String,
    @SerializedName("add_date") val addDate: String,
    @SerializedName("reject_reason") val rejectReason: String?,
    val username: String
) {
    suspend fun translateToRussian(): Comment {
        return this.copy(
            commentText = Translator.translate(commentText),
            rejectReason = rejectReason?.let { Translator.translate(it) },
            username = Translator.translate(username)
        )
    }
}

data class CommentCreate(
    @SerializedName("comment_text") val commentText: String
) {
    suspend fun translateToRussian(): CommentCreate {
        return this.copy(commentText = Translator.translate(commentText))
    }
}

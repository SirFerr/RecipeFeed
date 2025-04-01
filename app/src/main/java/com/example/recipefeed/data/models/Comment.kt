package com.example.recipefeed.data.models


import com.google.gson.annotations.SerializedName
import java.util.Date

data class Comment(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("recipe_id") val recipeId: Int,
    @SerializedName("comment_text") val commentText: String,
    @SerializedName("add_date") val addDate: String,
    @SerializedName("reject_reason") val rejectReason: String?
)

data class CommentCreate(
    @SerializedName("comment_text") val commentText: String
)
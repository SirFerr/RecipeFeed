package com.example.recipefeed.data.models

import com.google.gson.annotations.SerializedName

data class Moderator(
    @SerializedName("user_id") val userId: Int
)
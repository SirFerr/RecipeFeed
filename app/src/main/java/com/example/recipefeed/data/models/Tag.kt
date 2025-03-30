package com.example.recipefeed.data.models


import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class TagCreate(
    @SerializedName("name") val name: String
)
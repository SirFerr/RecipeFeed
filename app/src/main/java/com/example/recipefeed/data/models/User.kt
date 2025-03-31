package com.example.recipefeed.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("reg_date") val regDate: String?
)

data class UserCreate(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class Token(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String)
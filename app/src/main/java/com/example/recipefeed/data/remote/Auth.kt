package com.example.recipefeed.data.remote.auth

data class Auth(
    val name : String  = "",
    val email: String = "",
    val password: String = "",
    val token: String ="",
    val role: String = "USER"
)

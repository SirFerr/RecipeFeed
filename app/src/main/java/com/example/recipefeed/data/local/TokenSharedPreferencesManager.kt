package com.example.recipefeed.data.local

import android.content.Context
import androidx.core.content.edit

class TokenSharedPreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit {
            putString("auth_token", token)
        }
    }

    fun getToken(): String {
        return sharedPreferences.getString("auth_token", null) ?: ""
    }
    fun deleteToken() {
        sharedPreferences.edit{
            remove("auth_token")
        }
    }
}
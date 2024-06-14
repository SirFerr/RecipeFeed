package com.example.recipefeed.data.local

import android.content.Context
import android.util.Log
import androidx.core.content.edit

class TokenSharedPreferencesManager(context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME= "token"
        private const val TOKEN_NAME = "auth_token"
    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val trimmedToken = token.trim()
        Log.d("TokenSharedPreferences", "Saving token: '$trimmedToken'")
        sharedPreferences.edit {
            putString(TOKEN_NAME, trimmedToken)
        }
    }

    fun getToken(): String {
        val token = sharedPreferences.getString(TOKEN_NAME, "")?.trim() ?: ""
        Log.d("TokenSharedPreferences", "Retrieved token: '$token'")
        return token
    }

    fun deleteToken() {
        sharedPreferences.edit {
            remove(TOKEN_NAME)
        }
    }

}


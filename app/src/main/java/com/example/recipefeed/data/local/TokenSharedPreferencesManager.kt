package com.example.recipefeed.data.local

import android.content.Context
import android.util.Log
import androidx.core.content.edit

class TokenSharedPreferencesManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val trimmedToken = token.trim()
        Log.d("TokenSharedPreferences", "Saving token: '$trimmedToken'")
        sharedPreferences.edit {
            putString("auth_token", trimmedToken)
        }
    }

    fun getToken(): String {
        val token = sharedPreferences.getString(SHARED_PREFERENCES_NAME, "")?.trim() ?: ""
        Log.d("TokenSharedPreferences", "Retrieved token: '$token'")
        return token
    }

    fun deleteToken() {
        sharedPreferences.edit {
            remove(SHARED_PREFERENCES_NAME)
        }
    }
    companion object {
        private const val SHARED_PREFERENCES_NAME= "token"
    }
}


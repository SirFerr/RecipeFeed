package com.example.recipefeed.data.remote

import android.util.Log
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenSharedPreferencesManager.getToken().trim()
        Log.d("AuthInterceptor", "Token before adding to header: '$token'")
        if (token.isNotEmpty()) {
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(chain.request())
    }
}
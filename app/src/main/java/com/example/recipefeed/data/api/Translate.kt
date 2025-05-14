package com.example.recipefeed.data.api

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Ответ API
data class FreeTranslateResponse(
    @Json(name = "destination-text") val destinationText: String
)

// Интерфейс Free Translate API
interface FreeTranslateApi {
    @GET("/translate")
    suspend fun translate(
        @Query("text") text: String,
        @Query("dl") targetLang: String = "ru"
    ): FreeTranslateResponse
}

// Обёртка Translator
object Translator {
    private const val TAG = "Translator"

    private val api: FreeTranslateApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ftapi.pythonanywhere.com/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()

        retrofit.create(FreeTranslateApi::class.java)
    }

    private fun isTranslatable(text: String): Boolean {
        return text.any { it.isLetter() } && text.length > 3
    }

    suspend fun translate(text: String): String {
        Log.d(TAG, "🔍 Запрос на перевод: '$text'")
        return try {
            if (text.isBlank()) {
                Log.d(TAG, "ℹ️ Пропущен пустой текст")
                return text
            }

            if (!isTranslatable(text)) {
                Log.d(TAG, "ℹ️ Пропущен непереводимый текст: '$text'")
                return text
            }

            val response = api.translate(text)
            Log.d(TAG, "✅ Переведено: '$text' → '${response.destinationText}'")
            return response.destinationText
        } catch (e: Exception) {
            Log.e(TAG, "❌ Ошибка перевода '$text': ${e.message}")
            return text
        }
    }
}
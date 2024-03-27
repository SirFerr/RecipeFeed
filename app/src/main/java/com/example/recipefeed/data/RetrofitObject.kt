package com.example.recipefeed.data

import com.example.recipefeed.data.recipe.api.RecipeFeedService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitObject {
    private const val BASE_URL = "http://192.168.1.82:8080/api/"

    val api: RecipeFeedService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RecipeFeedService::class.java)
    }
}
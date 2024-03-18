package com.example.recipefeed.data

import com.example.recipefeed.data.recipe.api.RecipesService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitObject {
    private const val BASE_URL = "https://dummyjson.com/"

    val api: RecipesService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RecipesService::class.java)
    }
}
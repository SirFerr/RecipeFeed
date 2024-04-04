package com.example.recipefeed.di

import android.content.Context
import com.example.recipefeed.data.local.SharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(@ApplicationContext context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }


    @Singleton
    @Provides
    fun provideRecipeFeedApi(): RecipeFeedApi {

        val client = OkHttpClient.Builder()
            // Configure timeouts and other settings as needed
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true) // Enable retry on connection failure
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RecipeFeedApi::class.java)
    }
}

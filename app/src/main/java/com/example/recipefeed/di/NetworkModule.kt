package com.example.recipefeed.di

import android.content.Context
import com.example.recipefeed.data.api.ApiService
import com.example.recipefeed.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val token = sharedPreferences.getString("access_token", null)
                val request = chain.request().newBuilder()
                    .apply {
                        if (token != null) {
                            addHeader("Authorization", "Bearer $token")
                        }
                    }
                    .build()
                chain.proceed(request)
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://your-api-base-url/") // Замените на ваш базовый URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

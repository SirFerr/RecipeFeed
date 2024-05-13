package com.example.recipefeed.di

import android.content.Context
import com.example.recipefeed.data.local.SearchHistorySharedPreferencesManager
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideTokenSharedPreferencesManager(@ApplicationContext context: Context):TokenSharedPreferencesManager {
        return TokenSharedPreferencesManager(context)
    }
    @Singleton
    @Provides
    fun provideSearchHistorySharedPreferencesManager(@ApplicationContext context: Context): SearchHistorySharedPreferencesManager {
        return SearchHistorySharedPreferencesManager(context)
    }
}
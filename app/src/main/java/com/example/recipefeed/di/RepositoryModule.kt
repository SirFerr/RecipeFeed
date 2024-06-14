package com.example.recipefeed.di

import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.local.SearchHistorySharedPreferencesManager
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
//    @Singleton
//    @Provides
//    fun provideRepository(
//        recipeFeedApi: RecipeFeedApi,
//        searchHistorySharedPreferencesManager: SearchHistorySharedPreferencesManager,
//        tokenSharedPreferencesManager: TokenSharedPreferencesManager
//    ): Repository {
//        return Repository(
//            recipeFeedApi,
//            searchHistorySharedPreferencesManager,
//            tokenSharedPreferencesManager
//        )
//    }
}
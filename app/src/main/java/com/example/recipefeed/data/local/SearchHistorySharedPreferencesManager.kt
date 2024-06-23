package com.example.recipefeed.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class SearchHistorySharedPreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME= "search_history"
        private const val LAST_TEN_KEY = "last_ten"
    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveRequest(value: String) {
        if (value.isNotBlank()){
        val editor = sharedPreferences.edit()
        val list = sharedPreferences.getStringSet(LAST_TEN_KEY, setOf())?.toMutableList() ?: mutableListOf()
        list.remove(value)
        list.add(value)
        if (list.size > 10) {
            list.removeAt(0)
        }
        editor.putStringSet(LAST_TEN_KEY, list.toSet())
        editor.apply()}
    }


    fun getSearchHistory(): List<String> {
        return sharedPreferences.getStringSet(LAST_TEN_KEY, setOf())?.toList()
            ?: emptyList()
    }


}
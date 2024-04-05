package com.example.recipefeed.data.local

import android.content.Context

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    fun saveString(value: String) {
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


    fun getLastTenStrings(): List<String> {
        return sharedPreferences.getStringSet(LAST_TEN_KEY, setOf())?.toList()
            ?: emptyList()
    }

    companion object {
        private const val LAST_TEN_KEY = "last_ten"
    }
}
package com.example.recipefeed.data.local

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsSharedPreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "settings"
        private const val THEME = "THEME"
    }

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)


    fun setThemeIsDark(boolean: Boolean) {
        sharedPreferences.edit {
            putBoolean(THEME, boolean)
        }
    }

    fun isThemeDark(): Boolean {
        return sharedPreferences.getBoolean(THEME, true)
    }
}
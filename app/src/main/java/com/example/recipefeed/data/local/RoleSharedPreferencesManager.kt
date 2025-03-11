package com.example.recipefeed.data.local

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoleSharedPreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "role"
        private const val ROLE = "ROLE"
    }

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)


    fun setRoleIsModerator(boolean: Boolean) {
        sharedPreferences.edit {
            putBoolean(ROLE, boolean)
        }
    }

    fun isModerator(): Boolean {
        return sharedPreferences.getBoolean(ROLE, true)
    }
}

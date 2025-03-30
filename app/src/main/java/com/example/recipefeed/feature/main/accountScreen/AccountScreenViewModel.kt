package com.example.recipefeed.view.mainMenu.accountScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun deleteToken() {
        val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove("access_token")
            apply()
        }
    }
}
package com.example.recipefeed.view.mainMenu.accountScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(private val tokenSharedPreferencesManager: TokenSharedPreferencesManager) :ViewModel() {
    fun deleteToken(){
        tokenSharedPreferencesManager.deleteToken()
    }

}
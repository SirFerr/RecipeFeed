package com.example.recipefeed.view.loginAndSignUp.loginScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(private val tokenSharedPreferencesManager: TokenSharedPreferencesManager) :
    ViewModel() {
    var textUsername = MutableStateFlow("")
    var textPassword = MutableStateFlow("")
    val token = MutableStateFlow("")

    init {
        token.value = tokenSharedPreferencesManager.getToken()
    }

    fun saveToken(string: String) {
        tokenSharedPreferencesManager.saveToken(string)
    }
}
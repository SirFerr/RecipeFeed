package com.example.recipefeed.loginAndSignUp.loginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.auth.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager,
    private val recipeFeedApi: RecipeFeedApi
) :
    ViewModel() {
    var textUsername = MutableStateFlow("")
    var textPassword = MutableStateFlow("")
    val token = MutableStateFlow("")
    val isSuccessful = MutableStateFlow(false)

    init {
        token.value = tokenSharedPreferencesManager.getToken()
    }

    fun signIn() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = recipeFeedApi.signIn(
                    Auth(
                        textUsername.value,
                        textUsername.value,
                        textPassword.value,
                    )
                )
                isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {
                    token.value = response.body()?.token!!
                    saveToken(token.value)
                }
            } catch (e: Exception) {
                Log.e("signIn", e.message.toString())
                isSuccessful.value = false
            }
        }
    }

    fun saveToken(string: String) {
        tokenSharedPreferencesManager.saveToken(string)
    }
}
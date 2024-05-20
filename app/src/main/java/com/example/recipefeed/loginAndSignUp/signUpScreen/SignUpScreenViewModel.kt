package com.example.recipefeed.loginAndSignUp.signUpScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.data.remote.auth.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) :
    ViewModel() {
    var textUsername = MutableStateFlow("")
    var textPassword = MutableStateFlow("")
    var textPasswordAgain = MutableStateFlow("")
    val isSuccessful = MutableStateFlow(true)


    fun signUp() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = recipeFeedApi.signUp(
                    Auth(
                        textUsername.value,
                        textUsername.value,
                        textPassword.value,
                    )
                )
                Log.d("signUp", response.body().toString())
                isSuccessful.value = response.isSuccessful

            } catch (e: Exception) {
                isSuccessful.value = false
            }
        }
    }

}
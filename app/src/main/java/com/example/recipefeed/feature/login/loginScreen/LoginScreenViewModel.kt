package com.example.recipefeed.feature.login.loginScreen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _textUsername = mutableStateOf("")
    val textUsername: State<String> = _textUsername

    private val _textPassword = mutableStateOf("")
    val textPassword: State<String> = _textPassword

    private val _token = mutableStateOf("")
    val token: State<String> = _token

    private val _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    init {
        val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        _token.value = sharedPreferences.getString("access_token", "") ?: ""
    }

    fun setErrorMessage(string: String = "") {
        _errorMessage.value = string
    }

    fun setTextUsername(string: String) {
        _textUsername.value = string
    }

    fun setTextPassword(string: String) {
        _textPassword.value = string
    }

    fun signIn(isSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                if (textUsername.value.isNotEmpty() && textPassword.value.isNotEmpty()) {
                    val response = repository.login(textUsername.value, textPassword.value)
                    if (response.isSuccess) {
                        response.getOrNull()?.let { token ->
                            _token.value = token.accessToken
                            val sharedPreferences =
                                context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit().putString("access_token", token.accessToken)
                                .apply()
                            _isSuccessful.value = true
                            setErrorMessage()
                            isSuccess()
                        }
                    } else {
                        _isSuccessful.value = false
                        setErrorMessage(response.exceptionOrNull()?.message ?: "Login failed")
                    }
                } else {
                    setErrorMessage("All fields must be filled!")
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
                setErrorMessage("Error: ${e.message}")
            }
        }
    }
}
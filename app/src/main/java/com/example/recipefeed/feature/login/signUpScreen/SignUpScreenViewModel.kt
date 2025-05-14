package com.example.recipefeed.feature.login.signUpScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.models.UserCreate
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _textUsername = mutableStateOf("")
    val textUsername: State<String> = _textUsername

    private val _textPassword = mutableStateOf("")
    val textPassword: State<String> = _textPassword

    private val _textPasswordAgain = mutableStateOf("")
    val textPasswordAgain: State<String> = _textPasswordAgain

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun setTextUsername(string: String) {
        _textUsername.value = string
    }

    fun setTextPassword(string: String) {
        _textPassword.value = string
    }

    fun setTextPasswordAgain(string: String) {
        _textPasswordAgain.value = string
    }

    fun setErrorMessage(string: String = "") {
        _errorMessage.value = string
    }

    fun signUp(isSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                if (textUsername.value.isNotEmpty() && textPassword.value.isNotEmpty() && textPasswordAgain.value.isNotEmpty()) {
                    if (textPassword.value == textPasswordAgain.value) {
                        val userCreate = UserCreate(
                            username = textUsername.value,
                            password = textPassword.value
                        )
                        val response = repository.registerUser(userCreate)
                        if (response.isSuccess) {
                            setErrorMessage()
                            isSuccess()
                        } else {
                            setErrorMessage(
                                response.exceptionOrNull()?.message ?: "Регистрация не удалась"
                            )
                        }
                    } else {
                        setErrorMessage("Пароли не совпадают!")
                    }
                } else {
                    setErrorMessage("Все поля должны быть заполнены!")
                }
            } catch (e: Exception) {
                setErrorMessage("Ошибка: ${e.message}")
            }
        }
    }
}
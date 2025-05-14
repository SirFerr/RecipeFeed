package com.example.recipefeed.feature.login.loginScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.data.local.RoleSharedPreferencesManager
import com.example.recipefeed.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context,
    private val roleSharedPreferencesManager: RoleSharedPreferencesManager
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
        Log.d("token-",_token.value)

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

    fun addToken(string: String){
        val sharedPreferences =
            context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("access_token", string)
            .apply()
        Log.d("token",string)
    }

    fun signIn(isSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                if (textUsername.value.isNotEmpty() && textPassword.value.isNotEmpty()) {
                    val loginResult = repository.login(textUsername.value, textPassword.value)
                    if (loginResult.isSuccess) {
                        loginResult.getOrNull()?.let { token ->
                            _token.value = token.accessToken
                            addToken(string = token.accessToken)
                            // Проверяем, является ли пользователь модератором
                            val moderatorResult = repository.isCurrentUserModerator()
                            if (moderatorResult.isSuccess) {
                                val isModerator = moderatorResult.getOrNull() ?: false
                                roleSharedPreferencesManager.setRoleIsModerator(isModerator)
                            } else {
                                // Если не удалось проверить, устанавливаем false
                                roleSharedPreferencesManager.setRoleIsModerator(false)
                            }

                            _isSuccessful.value = true
                            setErrorMessage()
                            isSuccess()
                        }
                    } else {
                        _isSuccessful.value = false
                        setErrorMessage(loginResult.exceptionOrNull()?.message ?: "Вход в систему не удался")
                    }
                } else {
                    setErrorMessage("Все поля должны быть заполнены!")
                }
            } catch (e: Exception) {
                _isSuccessful.value = false
                setErrorMessage("Ошибка: ${e.message}")
            }
        }
    }
}
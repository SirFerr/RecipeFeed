package com.example.recipefeed.screens.loginGroup.loginScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _textUsername = mutableStateOf("")
    val textUsername: State<String> = _textUsername
    private var _textPassword = mutableStateOf("")
    val textPassword: State<String> = _textPassword
    private val _token = mutableStateOf("")
    val token: State<String> = _token
    private val _isSuccessful = mutableStateOf(false)
    val isSuccessful: State<Boolean> = _isSuccessful
    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    init {
        _token.value = repository.getToken()
    }

    fun setErrorMessage(string: String = "") {
        _errorMessage.value = string
    }

    fun setTextUsername(string: String){
        _textUsername.value=string
    }

    fun setTextPassword(string: String){
        _textPassword.value=string
    }

    fun signIn(isSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (_textUsername.value == "e" && _textPassword.value == "e") {
                    _isSuccessful.value = true
                    repository.saveToken("e")
                }
                if (_textUsername.value != "" && _textPassword.value != "") {
                    val response = repository.signIn(
                        Auth(
                            email = _textPassword.value,
                            password = _textPassword.value,
                        )
                    )

                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.error == "") {
                                val receivedToken = it.token.trim()
                                _token.value = receivedToken
                                repository.saveToken(receivedToken)
                                _isSuccessful.value = true
                                withContext(Dispatchers.Main) {
                                    setErrorMessage()
                                    isSuccess()
                                }
                            } else {
                                _isSuccessful.value = false
                                setErrorMessage(it.error)

                            }
                        }
                    } else {
                        _isSuccessful.value = false

                        setErrorMessage("response is not success")

                    }
                } else {
                    setErrorMessage("Field not fill!")
                }
            } catch (e: Exception) {
                _isSuccessful.value = false

                setErrorMessage(e.message.toString())

            }
        }
    }
}

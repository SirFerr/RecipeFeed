package com.example.recipefeed.feature.loginGroup.signUpScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import com.example.recipefeed.data.remote.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {


    private var _textUsername = mutableStateOf("")
    val textUsername: State<String> = _textUsername
    private var _textPassword = mutableStateOf("")
    val textPassword: State<String> = _textPassword
    private val _textPasswordAgain = mutableStateOf("")
    val textPasswordAgain: State<String> = _textPasswordAgain
    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun setTextUsername(string: String){
        _textUsername.value=string
    }

    fun setTextPassword(string: String){
        _textPassword.value=string
    }

    fun setTextPasswordAgain(string: String){
        _textPasswordAgain.value=string
    }



    fun setErrorMessage(string: String = "") {
        _errorMessage.value = string
    }

    fun signUp(isSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (textUsername.value != "" && textPassword.value != "" && textPasswordAgain.value != "") {
                    if (textPasswordAgain.value == textPassword.value) {
                        val response = repository.signUp(
                            Auth(
                                name = textUsername.value,
                                email = textUsername.value,
                                password = textPassword.value,
                                role = "USER"
                            )
                        )

                        if (response.body()?.error == "") {
                            withContext(Dispatchers.Main) {
                                isSuccess()
                            }
                        } else {
                            setErrorMessage(response.body()?.error.toString())

                        }
                    } else {
                        setErrorMessage("Password not compared!")

                    }
                } else {
                    setErrorMessage("Field not fill!")

                }


            } catch (e: Exception) {
                setErrorMessage("Response is not success " + e.toString())

            }
        }
    }

}
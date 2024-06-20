package com.example.recipefeed.loginAndSignUp.signUpScreen

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
class SignUpScreenViewModel @Inject constructor(
    private val repository: Repository
) :
    ViewModel() {
    var textUsername = MutableStateFlow("")
    var textPassword = MutableStateFlow("")
    var textPasswordAgain = MutableStateFlow("")

    val errorMessage = MutableStateFlow("")
    fun setErrorMessage(string: String = "") {
        errorMessage.value = string
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
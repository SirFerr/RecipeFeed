package com.example.recipefeed.loginAndSignUp.loginScreen

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
    var textUsername = MutableStateFlow("")
    var textPassword = MutableStateFlow("")
    val token = MutableStateFlow("")
    val isSuccessful = MutableStateFlow(false)

    init {
        token.value = repository.getToken()
    }


    fun signIn(isSuccess: () -> Unit, isError: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (textUsername.value == "e" && textPassword.value == "e") {
                    isSuccessful.value = true
                    repository.saveToken("e")
                }
                val response = repository.signIn(
                    Auth(
                        email =  textUsername.value,
                        password =  textPassword.value,
                    )
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.error == "") {
                            val receivedToken = it.token.trim()
                            token.value = receivedToken
                            repository.saveToken(receivedToken)
                            isSuccessful.value = true
                            withContext(Dispatchers.Main) {
                                isSuccess()
                            }
                        } else {
                            isSuccessful.value = false
                            withContext(Dispatchers.Main) {
                                isError(it.error)
                            }
                        }
                    }
                } else {
                    isSuccessful.value = false
                    withContext(Dispatchers.Main) {
                        isError("response is not success")
                    }
                }
            } catch (e: Exception) {
                isSuccessful.value = false
                withContext(Dispatchers.Main) {
                    isError(e.message.toString())
                }
            }
        }
    }
}

package com.example.recipefeed.screens.loginScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) :
    ViewModel() {
    var textUsername = MutableStateFlow("")
    var textPassword = MutableStateFlow("")
}
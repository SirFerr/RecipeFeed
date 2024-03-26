package com.example.recipefeed.ui.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TextFieldViewModel @Inject constructor() : ViewModel() {
    var textSearch = MutableStateFlow("")
    var textUsername = MutableStateFlow("")
    var textPassword = MutableStateFlow("")
    var textPasswordAgain = MutableStateFlow("")

}
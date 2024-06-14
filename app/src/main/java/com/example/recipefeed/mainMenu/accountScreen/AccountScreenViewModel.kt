package com.example.recipefeed.view.mainMenu.accountScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun deleteToken() {
        repository.deleteToken()
    }

}
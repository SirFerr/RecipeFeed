package com.example.recipefeed.feature.main.accountScreen.settingsScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val isThemeDark  = MutableStateFlow(repository.isThemeDark())

    fun changeThemeIsDark(){
        repository.setThemeIsDark(!isThemeDark.value)
        isThemeDark.value = !isThemeDark.value
    }

}
package com.example.recipefeed.screens.mainGroup.accountScreen.settingsScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.Repository
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
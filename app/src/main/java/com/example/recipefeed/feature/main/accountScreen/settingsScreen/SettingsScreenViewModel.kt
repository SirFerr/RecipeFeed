package com.example.recipefeed.feature.main.accountScreen.settingsScreen

import androidx.lifecycle.ViewModel
import com.example.recipefeed.data.local.AppSettingsSharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val appSettingsSharedPreferencesManager: AppSettingsSharedPreferencesManager): ViewModel() {

    val isThemeDark  = MutableStateFlow(appSettingsSharedPreferencesManager.isThemeDark())

    fun changeThemeIsDark(){
        appSettingsSharedPreferencesManager.setThemeIsDark(!isThemeDark.value)
        isThemeDark.value = !isThemeDark.value
    }

}
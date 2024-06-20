package com.example.recipefeed.screens.loginGroup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun customTextField(stringRes: String, textValue: MutableStateFlow<String>) {

    val text by textValue.collectAsState()
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        value = text,
        label = {
            Text(
                text = stringRes,
            )
        },
        onValueChange = { textValue.value = it })
}
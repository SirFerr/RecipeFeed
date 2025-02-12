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
fun CustomTextField(stringRes: String, text: String, onChange: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        value = text,
        label = {
            Text(
                text = stringRes,
            )
        },
        onValueChange = { onChange(it) })
}
package com.example.recipefeed.loginAndSignUp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ErrorMessage(textValue: MutableStateFlow<String>) {
    val text by textValue.collectAsState()

    Text(
        text = if (text.isNotEmpty()) text else "",
        color = MaterialTheme.colorScheme.error
    )
}
package com.example.recipefeed.feature.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorMessage(text: String) {


    Text(
        text = if (text.isNotEmpty()) text else "",
        color = MaterialTheme.colorScheme.error
    )
}
package com.example.recipefeed.feature.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
package com.example.recipefeed.feature.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun CustomTextField(
    stringRes: String,
    text: String,
    onFocusAction: () -> Unit = {},
    onValueChange: (String) -> Unit,

    ) {
    val focusManager = LocalFocusManager.current

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    LaunchedEffect(isFocused) { if (isFocused) onFocusAction() }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        value = text,
        interactionSource = interactionSource,
        label = {
            Text(
                text = stringRes,
            )
        },
        onValueChange = { onValueChange(it) })
}
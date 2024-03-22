package com.example.recipefeed.ui.view.screens.logInAndSignUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun spacer(screen: @Composable () -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(Modifier.weight(1f, true))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(6f, true),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            content = { screen() }
        )
        Spacer(Modifier.weight(1f, true))
    }
}

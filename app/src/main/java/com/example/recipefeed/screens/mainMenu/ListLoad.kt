package com.example.recipefeed.screens.mainMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.recipefeed.R

@Composable
fun CircularItem() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.main_padding)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier)
    }
}

@Composable
fun CardItem(exec: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.main_padding)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.main_padding)
            )
        ) {
            Text(text = "Network error")
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { exec() },
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(text = "Refresh")
                }
            }
        }
    }
}
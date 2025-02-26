package com.example.recipefeed.feature.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreenViewModel

@Composable
fun MainScreenButtons(onSwipeLeft:()->Unit,onSwipeRight:()->Unit) {
    Row(
        Modifier
            .wrapContentSize()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.wrapContentSize(),
            onClick = {
                onSwipeLeft()
            }) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null)
        }
        IconButton(modifier = Modifier.wrapContentSize(),
            onClick = {
                onSwipeRight()
            }) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
        }
    }
}
package com.example.recipefeed.mainMenu.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.recipefeed.mainMenu.scaffold.bottom.bottomNavigation
import com.example.recipefeed.utils.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithBottom(
    navController: NavHostController, screen: @Composable (PaddingValues) -> Unit
) {
    Scaffold(content = {
        screen(it)

    },
        bottomBar = {
            bottomNavigation(navController = navController)
        },
    )
}
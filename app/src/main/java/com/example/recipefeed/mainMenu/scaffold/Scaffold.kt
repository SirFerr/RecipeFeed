package com.example.recipefeed.mainMenu.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.recipefeed.mainMenu.scaffold.bottom.bottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithBottom(
    navController: NavHostController? = null, screen: @Composable (PaddingValues) -> Unit
) {
    Scaffold(content = {
            screen(it)

    }, bottomBar = {
        navController?.let { bottomNavigation(navController = it) }
    })
}
package com.example.recipefeed.ui.view.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.recipefeed.ui.view.scaffold.bottom.bottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun scaffold(
    navController: NavHostController? = null, screen: @Composable (PaddingValues) -> Unit
) {
    Scaffold(content = {

        screen(it)
    }, bottomBar = {
        navController?.let { bottomNavigation(navController = it) }
    })
}
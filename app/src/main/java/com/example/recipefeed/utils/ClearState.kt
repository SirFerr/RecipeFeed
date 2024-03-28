package com.example.recipefeed.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.recipefeed.ui.viewModel.RecipeViewModel

@Composable
fun clearState(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    LaunchedEffect(currentRoute) {
        if (currentRoute != "mainScreen") {
            recipeViewModel.clearState()
        }
    }
}
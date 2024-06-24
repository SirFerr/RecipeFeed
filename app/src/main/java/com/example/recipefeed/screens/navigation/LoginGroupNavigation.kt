package com.example.recipefeed.screens.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipefeed.screens.loginGroup.loginScreen.LogInScreen
import com.example.recipefeed.screens.loginGroup.signUpScreen.SignUpScreen

fun NavGraphBuilder.loginGroupGraph(navController: NavHostController) {
    navigation(Destinations.LoginGroup.Login.route, Destinations.LoginGroup.route) {
        composable(Destinations.LoginGroup.Login.route) { LogInScreen(navController = navController) }
        composable(Destinations.LoginGroup.SignUp.route) { SignUpScreen(navController = navController) }
    }
}
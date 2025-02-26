package com.example.recipefeed.feature.loginGroup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipefeed.feature.loginGroup.loginScreen.LogInScreen
import com.example.recipefeed.feature.loginGroup.loginScreen.LoginScreenViewModel
import com.example.recipefeed.feature.loginGroup.signUpScreen.SignUpScreen
import com.example.recipefeed.feature.loginGroup.signUpScreen.SignUpScreenViewModel
import com.example.recipefeed.feature.navigation.Destinations

@Composable
fun LoginGroupNavigation(
    navHostController: NavController = rememberNavController(),
    navController: NavHostController = rememberNavController()
) {
    val loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
    val signUpScreenViewModel: SignUpScreenViewModel = hiltViewModel()

    NavHost(navController = navController, Destinations.LoginGroup.Login.route) {
        composable(Destinations.LoginGroup.Login.route) {
            LogInScreen(
                loginScreenViewModel,
                onTokenIsNotEmpty = {
                    navHostController.navigate(Destinations.MainGroup.route) {
                        popUpTo(Destinations.LoginGroup.route) { inclusive = true }
                    }
                },
                onSuccess = {
                    navHostController.navigate(Destinations.MainGroup.route) {
                        popUpTo(Destinations.LoginGroup.route) { inclusive = true }
                    }
                },
                onSignUp = {
                    navController.navigate(Destinations.LoginGroup.SignUp.route)
                })
        }
        composable(Destinations.LoginGroup.SignUp.route) {
            SignUpScreen(signUpScreenViewModel, onBack = {
                navController.navigateUp()
            }, onSuccess = {
                navController.navigateUp()
            })
        }
    }
}
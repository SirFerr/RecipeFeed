package com.example.recipefeed.ui.view.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipefeed.ui.view.screens.logInAndSignUp.logInScreen
import com.example.recipefeed.ui.view.screens.logInAndSignUp.signUpScreen


@Composable
fun navigationLogIn(): NavHostController {

    val focusManager = LocalFocusManager.current

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "loginScreen",
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() }) {
        composable("loginScreen") { logInScreen(navController) }
        composable("signupScreen") { signUpScreen(navController) }
        composable("main") { navigationMain(navController) }
    }

    return navController
}
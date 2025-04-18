package com.example.recipefeed.feature.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.example.recipefeed.feature.login.LoginGroupNavigation
import com.example.recipefeed.feature.main.MainGroupNavigation
import com.example.recipefeed.feature.serverNotAvaivableScreen.ServerNotAvailableScreen


@Composable
fun Navigation(onThemeUpdated: () -> Unit): NavHostController {
    val focusManager = LocalFocusManager.current

    val navController = rememberNavController()

    // Anim settings
    val duration = 700
    val enterTransition = fadeIn(animationSpec = tween(duration))
    val exitTransition = fadeOut(animationSpec = tween(duration))

    NavHost(
        navController = navController,
        startDestination = Destinations.LoginGroup.route,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() },
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
    ) {
        composable(Destinations.LoginGroup.route){
            LoginGroupNavigation(navHostController = navController)
        }
        composable(Destinations.MainGroup.route){
            MainGroupNavigation(navHostController = navController, onThemeUpdated = onThemeUpdated)
        }

        composable(Destinations.ServerNotAvailable.route) {
            ServerNotAvailableScreen()
        }
    }

    return navController
}


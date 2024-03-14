package com.example.recipefeed.ui.view.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipefeed.ui.view.scaffold.scaffold
import com.example.recipefeed.ui.view.screen.mainMenu.accountScreen
import com.example.recipefeed.ui.view.screen.mainMenu.favoriteScreen
import com.example.recipefeed.ui.view.screen.mainMenu.mainsScreen.mainScreen
import com.example.recipefeed.ui.view.screen.mainMenu.recipeScreen
import com.example.recipefeed.ui.view.screen.mainMenu.searchScreen

@Composable
fun navigationMain(firstNavController: NavHostController): NavHostController {
    val navController = rememberNavController()
    scaffold(navController = navController, screen = {
        NavHost(
            navController = navController, startDestination = "mainScreen",
            modifier = Modifier.padding(it)
        ) {
            composable("mainScreen") {
                mainScreen(
                    navController = navController,
                )
            }
            composable("favoriteScreen") {
                favoriteScreen(
                    navController = navController,
                )
            }
            composable("searchScreen") {
                searchScreen(
                    navController = navController,
                )
            }
            composable("recipeScreen") {
                recipeScreen(
                    navController = navController,
                )
            }
            composable("accountScreen") {
                accountScreen(
                    navController,
                    firstNavController
                )
            }
        }
    })


    return navController
}
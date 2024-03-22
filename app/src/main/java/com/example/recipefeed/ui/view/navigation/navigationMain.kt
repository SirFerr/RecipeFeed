package com.example.recipefeed.ui.view.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipefeed.ui.view.scaffold.scaffold
import com.example.recipefeed.ui.view.screens.mainMenu.favoriteScreen
import com.example.recipefeed.ui.view.screens.mainMenu.recipeScreen
import com.example.recipefeed.ui.view.screens.mainMenu.searchScreen
import com.example.recipefeed.ui.view.screens.mainMenu.accountScreens.accountScreen
import com.example.recipefeed.ui.view.screens.mainMenu.accountScreens.newRecipeScreen
import com.example.recipefeed.ui.view.screens.mainMenu.accountScreens.addedRecipes.addedRecipesScreen
import com.example.recipefeed.ui.view.screens.mainMenu.accountScreens.addedRecipes.editRecipeScreen
import com.example.recipefeed.ui.view.screens.mainMenu.mainsScreens.mainScreen

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

            composable("accountScreen") {
                accountScreen(
                    navController,
                    firstNavController
                )
            }
            composable("recipeScreen/{id}", listOf(navArgument("id") {
                type = NavType.IntType
            })) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                Log.d("recipe", id.toString())
                recipeScreen(navController, id!!)
            }
            composable("newRecipeScreen") {
                newRecipeScreen(navController)
            }
            composable("addedRecipesScreen") {
                addedRecipesScreen(navController)
            }
            composable("editRecipeScreen/{id}", listOf(navArgument("id") {
                type = NavType.IntType
            })) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                Log.d("recipe", id.toString())
                editRecipeScreen(navController, id!!)
            }

        }
    })


    return navController
}
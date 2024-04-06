package com.example.recipefeed.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipefeed.scaffold.scaffold
import com.example.recipefeed.view.loginScreen.logInScreen
import com.example.recipefeed.view.mainMenu.accountScreen.accountScreen
import com.example.recipefeed.view.mainMenu.addedRecipesScreen.addedRecipesScreen
import com.example.recipefeed.view.mainMenu.editRecipeScreen.editRecipeScreen
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteRecipesViewModel
import com.example.recipefeed.view.mainMenu.favoriteScreen.favoriteScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreenViewModel
import com.example.recipefeed.view.mainMenu.mainsScreens.mainScreen
import com.example.recipefeed.view.mainMenu.newRecipeScreen.newRecipeScreen
import com.example.recipefeed.view.mainMenu.recipeScreen.recipeScreen
import com.example.recipefeed.view.mainMenu.searchScreen.SearchRecipesViewModel
import com.example.recipefeed.view.mainMenu.searchScreen.searchScreen
import com.example.recipefeed.view.signUpScreen.signUpScreen

@Composable
fun navigation(): NavHostController {

    val focusManager = LocalFocusManager.current
    val firstNavController = rememberNavController()

    NavHost(navController = firstNavController,
        startDestination = "loginScreen",
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() })
    {
        composable("loginScreen") { logInScreen(firstNavController) }
        composable("signupScreen") { signUpScreen(firstNavController) }
        composable("main") {
            val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
            val favoriteRecipesViewModel: FavoriteRecipesViewModel = hiltViewModel()
            val searchRecipesViewModel: SearchRecipesViewModel = hiltViewModel()

            val navController = rememberNavController()
            scaffold(navController = navController, screen = {
                NavHost(
                    navController = navController, startDestination = "mainScreen",
                    modifier = Modifier.padding(it)
                ) {
                    composable("mainScreen") {
                        mainScreen(
                            navController = navController, mainScreenViewModel
                        )

                    }
                    composable("favoriteScreen") {
                        favoriteScreen(
                            navController = navController, favoriteRecipesViewModel
                        )

                    }
                    composable("searchScreen") {
                        searchScreen(
                            navController = navController, searchRecipesViewModel
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
                        if (id != null) {
                            recipeScreen(navController, id)
                        }
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
                        if (id != null) {
                            editRecipeScreen(navController, id)
                        }
                    }

                }
            })

        }
    }

    return firstNavController
}
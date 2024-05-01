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
import androidx.navigation.navigation
import com.example.recipefeed.scaffold.scaffold
import com.example.recipefeed.view.loginAndSignUp.loginScreen.LogInScreen
import com.example.recipefeed.view.mainMenu.accountScreen.AccountScreen
import com.example.recipefeed.view.mainMenu.addedRecipesScreen.AddedRecipesScreen
import com.example.recipefeed.view.mainMenu.editRecipeScreen.EditRecipeScreen
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteRecipesViewModel
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreenViewModel
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreen
import com.example.recipefeed.view.mainMenu.newRecipeScreen.NewRecipeScreen
import com.example.recipefeed.view.mainMenu.recipeScreen.RecipeScreen
import com.example.recipefeed.view.mainMenu.searchScreen.SearchRecipesViewModel
import com.example.recipefeed.view.mainMenu.searchScreen.SearchScreen
import com.example.recipefeed.view.loginAndSignUp.signUpScreen.SignUpScreen

@Composable
fun navigation(): NavHostController {

    val focusManager = LocalFocusManager.current
    val firstNavController = rememberNavController()

    NavHost(navController = firstNavController,
        startDestination = "loginAndSignUp",
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() })
    {
        navigation("loginScreen", "loginAndSignUp") {
            composable("loginScreen") { LogInScreen(firstNavController) }
            composable("signupScreen") { SignUpScreen(firstNavController) }
        }
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
                        MainScreen(
                            navController = navController, mainScreenViewModel
                        )

                    }
                    composable("favoriteScreen") {
                        FavoriteScreen(
                            navController = navController, favoriteRecipesViewModel
                        )

                    }
                    composable("searchScreen") {
                        SearchScreen(
                            navController = navController, searchRecipesViewModel
                        )
                    }

                    composable("accountScreen") {
                        AccountScreen(
                            navController,
                            firstNavController
                        )
                    }
                    composable("recipeScreen/{id}", listOf(navArgument("id") {
                        type = NavType.IntType
                    })) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        if (id != null) {
                            RecipeScreen( id)
                        }
                    }
                    composable("newRecipeScreen") {
                        NewRecipeScreen(navController)
                    }
                    composable("addedRecipesScreen") {
                        AddedRecipesScreen(navController)
                    }
                    composable("editRecipeScreen/{id}", listOf(navArgument("id") {
                        type = NavType.IntType
                    })) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        if (id != null) {
                            EditRecipeScreen(navController, id)
                        }
                    }

                }
            })

        }
    }

    return firstNavController
}
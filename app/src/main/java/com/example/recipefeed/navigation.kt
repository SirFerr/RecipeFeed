package com.example.recipefeed

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipefeed.loginAndSignUp.loginScreen.LogInScreen
import com.example.recipefeed.loginAndSignUp.signUpScreen.SignUpScreen
import com.example.recipefeed.scaffold.scaffold
import com.example.recipefeed.utils.Destinations
import com.example.recipefeed.view.mainMenu.accountScreen.AccountScreen
import com.example.recipefeed.view.mainMenu.addedRecipesScreen.AddedRecipesScreen
import com.example.recipefeed.view.mainMenu.editRecipeScreen.EditRecipeScreen
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreen
import com.example.recipefeed.view.mainMenu.newRecipeScreen.NewRecipeScreen
import com.example.recipefeed.view.mainMenu.recipeScreen.RecipeScreen
import com.example.recipefeed.view.mainMenu.searchScreen.SearchScreen

@Composable
fun navigation(): NavHostController {
    val focusManager = LocalFocusManager.current
    val firstNavController = rememberNavController()
    val duration = 700
    NavHost(navController = firstNavController,
        startDestination = Destinations.LOGIN_GROUP,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() },
        enterTransition = { fadeIn(animationSpec = tween(duration)) },
        exitTransition = { fadeOut(animationSpec = tween(duration)) })
    {
        navigation(Destinations.LOGIN, Destinations.LOGIN_GROUP) {
            composable(Destinations.LOGIN) { LogInScreen(firstNavController) }
            composable(Destinations.SIGNUP) { SignUpScreen(firstNavController) }
        }
        composable(Destinations.MAIN_GROUP) {
            val navController = rememberNavController()
            scaffold(navController = navController, screen = {
                NavHost(
                    navController = navController,
                    startDestination = Destinations.MAIN,
                    modifier = Modifier.padding(it),
                    enterTransition = { fadeIn(animationSpec = tween(duration)) },
                    exitTransition = { fadeOut(animationSpec = tween(duration)) }
                ) {
                    composable(Destinations.MAIN) {
                        MainScreen(
                            navController = navController
                        )

                    }
                    composable(Destinations.FAVORITE) {
                        FavoriteScreen(
                            navController = navController
                        )

                    }
                    composable(Destinations.SEARCH) {
                        SearchScreen(
                            navController = navController
                        )
                    }

                    composable(Destinations.ACCOUNT) {
                        AccountScreen(
                            navController,
                            firstNavController
                        )
                    }
                    composable("${Destinations.RECIPE}/{id}", listOf(navArgument("id") {
                        type = NavType.IntType
                    })) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        if (id != null) {
                            RecipeScreen(id)
                        }
                    }
                    composable(Destinations.NEW_RECIPE) {
                        NewRecipeScreen(navController)
                    }
                    composable(Destinations.ADDED_RECIPES) {
                        AddedRecipesScreen(navController)
                    }
                    composable("${Destinations.EDIT_RECIPE}/{id}", listOf(navArgument("id") {
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
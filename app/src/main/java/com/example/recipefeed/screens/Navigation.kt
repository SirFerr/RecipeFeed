package com.example.recipefeed.screens

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
import com.example.recipefeed.screens.loginGroup.loginScreen.LogInScreen
import com.example.recipefeed.screens.loginGroup.signUpScreen.SignUpScreen
import com.example.recipefeed.screens.mainGroup.accountScreen.settingsScreen.SettingsScreen
import com.example.recipefeed.screens.mainGroup.scaffold.ScaffoldWithBottom
import com.example.recipefeed.screens.serverNotAvaivableScreen.ServerNotAvailableScreen
import com.example.recipefeed.view.mainMenu.accountScreen.AccountScreen
import com.example.recipefeed.view.mainMenu.addedRecipesScreen.AddedRecipesScreen
import com.example.recipefeed.view.mainMenu.editRecipeScreen.EditRecipeScreen
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreen
import com.example.recipefeed.view.mainMenu.newRecipeScreen.NewRecipeScreen
import com.example.recipefeed.view.mainMenu.recipeScreen.RecipeScreen
import com.example.recipefeed.view.mainMenu.searchScreen.SearchScreen


object Destinations {
    const val LOGIN_GROUP = "LOGIN_GROUP"
    const val LOGIN = "LOGIN"
    const val SIGNUP = "SIGNUP"

    const val MAIN_GROUP = "MAIN_GROUP"
    const val MAIN = "MAIN"
    const val SEARCH = "SEARCH"
    const val RECIPE = "RECIPE"
    const val FAVORITE= "FAVORITE"
    const val ACCOUNT = "ACCOUNT"
    const val NEW_RECIPE = "NEW_RECIPE"
    const val ADDED_RECIPES="ADDED_RECIPES"
    const val EDIT_RECIPE = "EDIT_RECIPE"
    const val SETTINGS = "SETTINGS"

    const val SERVER_NOT_AVAILABLE ="SERVER_NOT_AVAILABLE"
}

@Composable
fun Navigation( onThemeUpdated: () -> Unit): NavHostController {
    val focusManager = LocalFocusManager.current

    val firstNavController = rememberNavController()

    //Anim settings
    val duration = 700
    val enterTransition = fadeIn(animationSpec = tween(duration))
    val exitTransition = fadeOut(animationSpec = tween(duration))


    NavHost(
        navController = firstNavController,
        startDestination = Destinations.LOGIN_GROUP,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() },
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
    )
    {

        navigation(Destinations.LOGIN, Destinations.LOGIN_GROUP) {
            composable(Destinations.LOGIN) { LogInScreen(navController = firstNavController) }
            composable(Destinations.SIGNUP) { SignUpScreen(navController = firstNavController) }
        }
        composable(Destinations.SERVER_NOT_AVAILABLE){
            ServerNotAvailableScreen()
        }
        composable(Destinations.MAIN_GROUP) {
            val navController = rememberNavController()
            ScaffoldWithBottom(navController = navController, screen = {
                NavHost(
                    navController = navController,
                    startDestination = Destinations.MAIN,
                    modifier = Modifier.padding(it),
                    enterTransition = { enterTransition },
                    exitTransition = { exitTransition }
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
                            navController =  navController,
                            firstNavController = firstNavController
                        )
                    }
                    composable("${Destinations.RECIPE}/{id}", listOf(navArgument("id") {
                        type = NavType.IntType
                    })) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        if (id != null) {
                            RecipeScreen(id = id, navController = navController)
                        }
                    }
                    composable(Destinations.NEW_RECIPE) {
                        NewRecipeScreen(navController = navController)
                    }
                    composable(Destinations.ADDED_RECIPES) {
                        AddedRecipesScreen(navController = navController)
                    }
                    composable(Destinations.SETTINGS) {
                        SettingsScreen(onThemeUpdated = onThemeUpdated,navController = navController)
                    }
                    composable("${Destinations.EDIT_RECIPE}/{id}", listOf(navArgument("id") {
                        type = NavType.IntType
                    })) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        if (id != null) {
                            EditRecipeScreen(navController = navController, id = id)
                        }
                    }

                }
            })

        }
    }

    return firstNavController
}
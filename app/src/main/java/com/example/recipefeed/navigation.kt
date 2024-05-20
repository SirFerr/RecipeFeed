package com.example.recipefeed.view

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipefeed.data.local.TokenSharedPreferencesManager
import com.example.recipefeed.scaffold.scaffold
import com.example.recipefeed.utils.Destinations
import com.example.recipefeed.view.loginAndSignUp.loginScreen.LogInScreen
import com.example.recipefeed.view.loginAndSignUp.signUpScreen.SignUpScreen
import com.example.recipefeed.view.mainMenu.accountScreen.AccountScreen
import com.example.recipefeed.view.mainMenu.addedRecipesScreen.AddedRecipesScreen
import com.example.recipefeed.view.mainMenu.editRecipeScreen.EditRecipeScreen
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteRecipesViewModel
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreenViewModel
import com.example.recipefeed.view.mainMenu.newRecipeScreen.NewRecipeScreen
import com.example.recipefeed.view.mainMenu.recipeScreen.RecipeScreen
import com.example.recipefeed.view.mainMenu.searchScreen.SearchRecipesViewModel
import com.example.recipefeed.view.mainMenu.searchScreen.SearchScreen

@Composable
fun navigation(): NavHostController {
    val focusManager = LocalFocusManager.current
    val firstNavController = rememberNavController()
    val duration = 700
    NavHost(navController = firstNavController,
        startDestination = Destinations.loginGroup,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() },
        enterTransition = { fadeIn(animationSpec = tween(duration)) },
        exitTransition = { fadeOut(animationSpec = tween(duration)) })
    {
        navigation(Destinations.login, Destinations.loginGroup) {
            composable(Destinations.login) { LogInScreen(firstNavController) }
            composable(Destinations.signUp) { SignUpScreen(firstNavController) }
        }
        composable(Destinations.mainGroup) {
            val navController = rememberNavController()
            scaffold(navController = navController, screen = {
                NavHost(
                    navController = navController,
                    startDestination = Destinations.main,
                    modifier = Modifier.padding(it),
                    enterTransition = { fadeIn(animationSpec = tween(duration)) },
                    exitTransition = { fadeOut(animationSpec = tween(duration)) }
                ) {
                    composable(Destinations.main) {
                        MainScreen(
                            navController = navController
                        )

                    }
                    composable(Destinations.favorite) {
                        FavoriteScreen(
                            navController = navController
                        )

                    }
                    composable(Destinations.search) {
                        SearchScreen(
                            navController = navController
                        )
                    }

                    composable(Destinations.account) {
                        AccountScreen(
                            navController,
                            firstNavController
                        )
                    }
                    composable("${Destinations.recipe}/{id}", listOf(navArgument("id") {
                        type = NavType.IntType
                    })) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        if (id != null) {
                            RecipeScreen(id)
                        }
                    }
                    composable(Destinations.newRecipe) {
                        NewRecipeScreen(navController)
                    }
                    composable(Destinations.addedRecipes) {
                        AddedRecipesScreen(navController)
                    }
                    composable("${Destinations.editRecipe}/{id}", listOf(navArgument("id") {
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
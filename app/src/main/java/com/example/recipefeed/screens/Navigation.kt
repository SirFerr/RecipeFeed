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
import androidx.navigation.NavGraphBuilder
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

sealed class Destinations(val route: String) {
    object LoginGroup : Destinations("LOGIN_GROUP") {
        object Login : Destinations("LOGIN")
        object SignUp : Destinations("SIGNUP")
    }

    object MainGroup : Destinations("MAIN_GROUP") {
        object Main : Destinations("MAIN")
        object Search : Destinations("SEARCH")
        object Recipe : Destinations("RECIPE")
        object Favorite : Destinations("FAVORITE")
        object Account : Destinations("ACCOUNT")
        object NewRecipe : Destinations("NEW_RECIPE")
        object AddedRecipes : Destinations("ADDED_RECIPES")
        object EditRecipe : Destinations("EDIT_RECIPE")
        object Settings : Destinations("SETTINGS")
    }

    object ServerNotAvailable : Destinations("SERVER_NOT_AVAILABLE")
}

@Composable
fun Navigation(onThemeUpdated: () -> Unit): NavHostController {
    val focusManager = LocalFocusManager.current

    val firstNavController = rememberNavController()

    // Anim settings
    val duration = 700
    val enterTransition = fadeIn(animationSpec = tween(duration))
    val exitTransition = fadeOut(animationSpec = tween(duration))

    NavHost(
        navController = firstNavController,
        startDestination = Destinations.LoginGroup.route,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() },
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
    ) {
        loginGraph(firstNavController)
        serverNotAvailableGraph()
        mainGraph(firstNavController, onThemeUpdated)
    }

    return firstNavController
}

private fun NavGraphBuilder.loginGraph(navController: NavHostController) {
    navigation(Destinations.LoginGroup.Login.route, Destinations.LoginGroup.route) {
        composable(Destinations.LoginGroup.Login.route) { LogInScreen(navController = navController) }
        composable(Destinations.LoginGroup.SignUp.route) { SignUpScreen(navController = navController) }
    }
}

private fun NavGraphBuilder.serverNotAvailableGraph() {
    composable(Destinations.ServerNotAvailable.route) {
        ServerNotAvailableScreen()
    }
}

private fun NavGraphBuilder.mainGraph(firstNavController: NavHostController, onThemeUpdated: () -> Unit) {

    // Anim settings
    val duration = 700
    val enterTransition = fadeIn(animationSpec = tween(duration))
    val exitTransition = fadeOut(animationSpec = tween(duration))

    composable(Destinations.MainGroup.route) {
        val navController = rememberNavController()
        ScaffoldWithBottom(navController = navController, screen = {
            NavHost(
                navController = navController,
                startDestination = Destinations.MainGroup.Main.route,
                modifier = Modifier.padding(it),
                enterTransition = { enterTransition },
                exitTransition = { exitTransition }
            ) {
                mainScreenGraph(navController, firstNavController, onThemeUpdated)
            }
        })
    }
}

private fun NavGraphBuilder.mainScreenGraph(navController: NavHostController, firstNavController: NavHostController, onThemeUpdated: () -> Unit) {
    composable(Destinations.MainGroup.Main.route) {
        MainScreen(navController = navController)
    }
    composable(Destinations.MainGroup.Favorite.route) {
        FavoriteScreen(navController = navController)
    }
    composable(Destinations.MainGroup.Search.route) {
        SearchScreen(navController = navController)
    }
    composable(Destinations.MainGroup.Account.route) {
        AccountScreen(navController = navController, firstNavController = firstNavController)
    }
    composable("${Destinations.MainGroup.Recipe.route}/{id}", listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id")
        if (id != null) {
            RecipeScreen(id = id, navController = navController)
        }
    }
    composable(Destinations.MainGroup.NewRecipe.route) {
        NewRecipeScreen(navController = navController)
    }
    composable(Destinations.MainGroup.AddedRecipes.route) {
        AddedRecipesScreen(navController = navController)
    }
    composable(Destinations.MainGroup.Settings.route) {
        SettingsScreen(onThemeUpdated = onThemeUpdated, navController = navController)
    }
    composable("${Destinations.MainGroup.EditRecipe.route}/{id}", listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id")
        if (id != null) {
            EditRecipeScreen(navController = navController, id = id)
        }
    }
}
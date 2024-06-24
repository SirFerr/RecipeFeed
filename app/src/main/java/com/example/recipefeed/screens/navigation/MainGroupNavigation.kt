package com.example.recipefeed.screens.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipefeed.screens.mainGroup.accountScreen.settingsScreen.SettingsScreen
import com.example.recipefeed.screens.mainGroup.scaffold.ScaffoldWithBottom
import com.example.recipefeed.view.mainMenu.accountScreen.AccountScreen
import com.example.recipefeed.view.mainMenu.addedRecipesScreen.AddedRecipesScreen
import com.example.recipefeed.view.mainMenu.editRecipeScreen.EditRecipeScreen
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreen
import com.example.recipefeed.view.mainMenu.newRecipeScreen.NewRecipeScreen
import com.example.recipefeed.view.mainMenu.recipeScreen.RecipeScreen
import com.example.recipefeed.view.mainMenu.searchScreen.SearchScreen

fun NavGraphBuilder.mainGroupGraph(firstNavController: NavHostController, onThemeUpdated: () -> Unit) {

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